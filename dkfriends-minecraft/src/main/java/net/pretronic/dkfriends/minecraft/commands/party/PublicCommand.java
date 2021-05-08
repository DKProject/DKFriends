package net.pretronic.dkfriends.minecraft.commands.party;

import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.party.PartyRole;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

public class PublicCommand extends BasicCommand {

    public PublicCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("public","private","p"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        Party party = player.getParty();
        if(party == null){
            sender.sendMessage(Messages.ERROR_PARTY_NOT);
            return;
        }

        PartyMember own = party.getMember(player.getId());
        if(own.getRole() != PartyRole.LEADER){
            sender.sendMessage(Messages.ERROR_PARTY_NOT_ALLOWED);
            return;
        }

        party.setPublic(!party.isPublic());
    }
}
