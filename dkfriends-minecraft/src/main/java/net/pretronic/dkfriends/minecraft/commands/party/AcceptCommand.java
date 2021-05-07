package net.pretronic.dkfriends.minecraft.commands.party;

import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.dkfriends.api.party.PartyManager;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.network.component.server.MinecraftServer;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.player.OnlineMinecraftPlayer;

import java.util.UUID;

public class AcceptCommand extends BasicCommand {

    private final PartyManager partyManager;

    public AcceptCommand(ObjectOwner owner,PartyManager partyManager) {
        super(owner, CommandConfiguration.name("accept","a","join"));
        this.partyManager = partyManager;
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        if(arguments.length < 1){
            sender.sendMessage(Messages.COMMAND_PARTY_HELP);
            return;
        }
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        Party party = partyManager.getParty(UUID.fromString(arguments[0]));
        if(party == null){
            sender.sendMessage(Messages.ERROR_PARTY_INVITATION_NOT);
            return;
        }

        if(party.isPublic()){
            if (checkParty(sender, arguments, player, party)) return;
            party.addMember(player.getId());
        }else{
            PartyInvitation invitation = party.getInvitation(player.getId());

            if(invitation == null){
                sender.sendMessage(Messages.ERROR_PARTY_INVITATION_NOT);
                return;
            }

            if (checkParty(sender, arguments, player, party)) return;
            invitation.accept();
        }
    }

    private boolean checkParty(CommandSender sender, String[] arguments, DKFriendsPlayer player, Party party) {
        boolean force = arguments.length > 1 && arguments[1].equalsIgnoreCase("force");
        if(player.isInParty()){
            if(force){
                party.leaveMember(player.getId());
            }else{
                sender.sendMessage(Messages.ERROR_PARTY_ALREADY);
                return true;
            }
        }
        return false;
    }

}
