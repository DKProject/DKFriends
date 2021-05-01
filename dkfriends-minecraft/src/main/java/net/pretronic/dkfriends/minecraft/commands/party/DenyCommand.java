package net.pretronic.dkfriends.minecraft.commands.party;

import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.dkfriends.api.party.PartyManager;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

import java.util.UUID;

public class DenyCommand extends BasicCommand {

    private final PartyManager partyManager;

    public DenyCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("deny","d"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        if(arguments.length < 1){
            //@Todo help message
            return;
        }
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        Party party = partyManager.getParty(UUID.fromString(arguments[0]));
        if(party == null){
            sender.sendMessage(Messages.ERROR_PARTY_INVITATION_NOT);
            return;
        }
        PartyInvitation invitation = party.getInvitation(player.getId());

        if(invitation == null){
            sender.sendMessage(Messages.ERROR_PARTY_INVITATION_NOT);
            return;
        }

        invitation.deny();
    }
}
