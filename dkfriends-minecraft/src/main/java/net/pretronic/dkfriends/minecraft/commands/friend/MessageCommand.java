package net.pretronic.dkfriends.minecraft.commands.friend;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.settings.PlayerSettings;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.Completable;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.player.OnlineMinecraftPlayer;

import java.util.Collection;

public class MessageCommand extends BasicCommand implements Completable {

    public MessageCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("message","msg","m"));
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

        if(!player.isFriend(target.getUniqueId())){
            sender.sendMessage(Messages.ERROR_FRIEND_NOT, VariableSet.create()
                    .addDescribed("player",target));
            return;
        }

        OnlineMinecraftPlayer onlineTarget = target.getAsOnlinePlayer();
        if(onlineTarget == null){
            sender.sendMessage(Messages.ERROR_PLAYER_NOT_ONLINE, VariableSet.create()
                    .addDescribed("prefix",Messages.PREFIX_FRIEND));
            return;
        }

        DKFriendsPlayer targetFriend = target.getAs(DKFriendsPlayer.class);
        if(!targetFriend.isActionAllow(PlayerSettings.FRIEND_ALLOW_MESSAGES,player)){
            sender.sendMessage(Messages.COMMAND_FRIEND_MESSAGE_NOT_ALLOWED, VariableSet.create()
                    .addDescribed("player",target));
            return;
        }

        String message = CommandUtil.readStringFromArguments(arguments,1);

        onlineTarget.sendMessage(Messages.COMMAND_FRIEND_MESSAGE,VariableSet.create()
                .addDescribed("message",message)
                .addDescribed("sender",sender));
    }

    @Override
    public Collection<String> complete(CommandSender sender, String[] args) {
        return CommandUtil.completeOnlineFriends(sender,args);
    }
}
