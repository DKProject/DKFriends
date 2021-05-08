package net.pretronic.dkfriends.minecraft.commands.clan;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

public class LeaveCommand extends BasicCommand {

    public LeaveCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("leave"));
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        DKFriendsPlayer player = ((MinecraftPlayer)commandSender).getAs(DKFriendsPlayer.class);
        if(CommandUtil.isInClanCheck(commandSender, player)) {
            player.leaveClan();
            commandSender.sendMessage(Messages.COMMAND_CLAN_LEAVE);
        }
    }
}
