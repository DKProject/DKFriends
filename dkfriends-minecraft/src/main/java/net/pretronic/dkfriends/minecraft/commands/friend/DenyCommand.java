package net.pretronic.dkfriends.minecraft.commands.friend;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;
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

import java.util.Collection;
import java.util.Collections;

public class DenyCommand extends BasicCommand implements Completable {

    public DenyCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("deny"));
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

        FriendRequest request = player.getFriendRequest(target.getUniqueId());

        if(request == null){
            sender.sendMessage(Messages.ERROR_FRIEND_REQUEST_NOT, VariableSet.create()
                    .addDescribed("player",target));
            return;
        }

        player.denyFriendRequest(request);
    }

    @Override
    public Collection<String> complete(CommandSender sender, String[] args) {
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);
        if(args.length == 0){
            return Iterators.map(player.getFriendRequests(), request -> request.getRequester().getName());
        }else  if(args.length == 1){
            return Iterators.map(player.getFriendRequests(), request -> request.getRequester().getName()
                    , request -> request.getRequester().getName().toLowerCase().startsWith(args[0].toLowerCase()));
        }else {
            return Collections.emptyList();
        }
    }
}
