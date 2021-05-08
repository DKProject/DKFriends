package net.pretronic.dkfriends.minecraft.commands.clan;

import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

public class PartyCommand extends BasicCommand {

    public PartyCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("party"));
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        DKFriendsPlayer player = ((MinecraftPlayer)commandSender).getAs(DKFriendsPlayer.class);
        if(CommandUtil.isInClanCheck(commandSender, player)) {
            if(CommandUtil.isInPartyCheck(commandSender, player)) {
                Party party = player.getParty();
                for (ClanMember onlineMember : player.getClan().getOnlineMembers()) {
                    if(party.isMember(onlineMember.getPlayerId())) continue;
                    party.invite(player, onlineMember.getPlayerId());
                }
                commandSender.sendMessage(Messages.COMMAND_CLAN_PARTY);
            }
        }
    }
}
