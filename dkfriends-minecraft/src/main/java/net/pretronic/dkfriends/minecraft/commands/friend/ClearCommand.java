package net.pretronic.dkfriends.minecraft.commands.friend;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

public class ClearCommand extends BasicCommand {

    public ClearCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("clear"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        if(player.getFriends().isEmpty()){
            sender.sendMessage(Messages.ERROR_FRIEND_EMPTY);
            return;
        }

        player.clearFriends();
        sender.sendMessage(Messages.COMMAND_FRIEND_CLEAR);
    }
}
