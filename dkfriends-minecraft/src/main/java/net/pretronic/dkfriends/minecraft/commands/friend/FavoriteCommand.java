package net.pretronic.dkfriends.minecraft.commands.friend;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.Friend;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.Completable;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.Iterators;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class FavoriteCommand extends BasicCommand implements Completable {

    public FavoriteCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("favorite"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        if(arguments.length < 1){
            sender.sendMessage(Messages.COMMAND_FRIEND_HELP);
            return;
        }

        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        MinecraftPlayer target = CommandUtil.getPlayer(sender, Messages.PREFIX_FRIEND,arguments[0]);
        if(target == null) return;

        Friend friend = player.getFriend(target.getUniqueId());
        if(friend == null){
            sender.sendMessage(Messages.ERROR_FRIEND_NOT, VariableSet.create()
                    .addDescribed("player",target));
            return;
        }

        friend.setFavorite(!friend.isFavorite());

        if(friend.isFavorite()){
            sender.sendMessage(Messages.COMMAND_FRIEND_FAVORITE_MARK,VariableSet.create()
                    .addDescribed("player",target));
        }else{
            sender.sendMessage(Messages.COMMAND_FRIEND_FAVORITE_UNMARKT,VariableSet.create()
                    .addDescribed("player",target));
        }
    }

    @Override
    public Collection<String> complete(CommandSender sender, String[] args) {
        return CommandUtil.completeFriends(sender,args);
    }
}
