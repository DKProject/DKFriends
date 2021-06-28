package net.pretronic.dkfriends.minecraft.commands.friend;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.settings.PlayerSettings;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.network.component.server.MinecraftServer;
import org.mcnative.runtime.api.player.ConnectedMinecraftPlayer;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.player.OnlineMinecraftPlayer;

public class JumpCommand extends BasicCommand {

    public JumpCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("jump","j"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        if(arguments.length < 1){
            //@Todo help message
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

        DKFriendsPlayer targetFriend = onlineTarget.getAs(DKFriendsPlayer.class);
        if(!targetFriend.isActionAllow(PlayerSettings.FRIEND_ALLOW_JUMP,player)){
            sender.sendMessage(Messages.COMMAND_FRIEND_JUMP_NOT_ALLOWED, VariableSet.create()
                    .addDescribed("player",target));
            return;
        }

        MinecraftServer current = ((ConnectedMinecraftPlayer)sender).getServer();

        MinecraftServer server = onlineTarget.getServer();
        if(server == null || server.getName().equals(current.getName())){
            sender.sendMessage(Messages.COMMAND_FRIEND_JUMP_ALREADY);
            return;
        }

        sender.sendMessage(Messages.COMMAND_FRIEND_JUMP_SUCCESS);
        ((ConnectedMinecraftPlayer)sender).connect(server);
    }
}
