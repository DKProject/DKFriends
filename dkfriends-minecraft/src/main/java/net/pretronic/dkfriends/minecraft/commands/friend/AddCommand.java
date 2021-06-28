package net.pretronic.dkfriends.minecraft.commands.friend;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.settings.PlayerSettings;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.MainCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

public class AddCommand extends BasicCommand {

    public AddCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("add","a"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        if(arguments.length < 1){
            sender.sendMessage(Messages.COMMAND_FRIEND_HELP);
            return;
        }
        if(CommandUtil.isSelfCheck(Messages.PREFIX_FRIEND,sender,arguments[0])) return;
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        MinecraftPlayer target = CommandUtil.getPlayer(sender,Messages.PREFIX_FRIEND,arguments[0]);
        if(target == null) return;

        if(player.isFriend(target.getUniqueId())){
            sender.sendMessage(Messages.ERROR_FRIEND_ALREADY, VariableSet.create()
                    .addDescribed("player",target));
            return;
        }

        if(player.hasFriendRequest(target.getUniqueId())){
            player.acceptFriendRequest(target.getUniqueId());
            return;
        }

        DKFriendsPlayer targetFriend = target.getAs(DKFriendsPlayer.class);
        if(!targetFriend.isActionAllow(PlayerSettings.FRIEND_ALLOW_REQUESTS,player)){
            sender.sendMessage(Messages.ERROR_FRIEND_REQUEST_NOT_ALLOWED, VariableSet.create()
                    .addDescribed("player",target));
            return;
        }

        if(targetFriend.hasFriendRequest(player)){
            sender.sendMessage(Messages.ERROR_FRIEND_REQUEST_ALREADY, VariableSet.create()
                    .addDescribed("player",target));
            return;
        }

        targetFriend.sendFriendRequest(player.getId());
        sender.sendMessage(Messages.COMMAND_FRIEND_ADD_SUCCESS, VariableSet.create()
                .addDescribed("player",target));

    }
}
