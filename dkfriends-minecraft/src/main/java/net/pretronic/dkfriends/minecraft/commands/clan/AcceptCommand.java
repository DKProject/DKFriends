package net.pretronic.dkfriends.minecraft.commands.clan;

import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.Completable;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

import java.util.Collection;

public class AcceptCommand extends BasicCommand {

    public AcceptCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("accept"));
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(args.length != 1) {
            commandSender.sendMessage(Messages.COMMAND_CLAN_HELP);
            return;
        }
        MinecraftPlayer player = (MinecraftPlayer) commandSender;
        DKFriendsPlayer friendsPlayer = player.getAs(DKFriendsPlayer.class);

        if(CommandUtil.isNotInClan(commandSender, friendsPlayer)) {
            String clanName = args[0];
            ClanInvitation invitation = friendsPlayer.getClanInvitation(clanName);
            if(invitation == null) {
                commandSender.sendMessage(Messages.ERROR_CLAN_MEMBER_NO_INVITATION_SPECIFIC, VariableSet.create().add("name", clanName));
                return;
            }
            if(invitation.accept() != null) {
                commandSender.sendMessage(Messages.COMMAND_CLAN_ACCEPT, VariableSet.create().addDescribed("clan", invitation.getClan()));
            } else {
                //@Todo cancelled message
            }
        }
    }
}
