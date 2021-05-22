package net.pretronic.dkfriends.minecraft.commands.party;

import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyManager;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

import java.util.Collection;

public class ListCommand extends BasicCommand {

    private final PartyManager partyManager;

    public ListCommand(ObjectOwner owner,PartyManager partyManager) {
        super(owner, CommandConfiguration.name("list","l"));
        this.partyManager = partyManager;
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        Collection<Party> parties = partyManager.getPublicParties();

        if(parties.size() == 0){
            sender.sendMessage(Messages.COMMAND_PARTY_PUBLIC_EMPTY);
            return;
        }

        sender.sendMessage(Messages.COMMAND_PARTY_PUBLIC_LIST, VariableSet.create()
                .addDescribed("parties",parties));
    }
}
