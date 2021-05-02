package net.pretronic.dkfriends.minecraft.commands.party;

import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

public class InfoCommand extends BasicCommand {

    public InfoCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("info","i"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        Party party = player.getParty();
        if(party == null){
            sender.sendMessage(Messages.ERROR_PARTY_NOT);
            return;
        }

        sender.sendMessage(Messages.COMMAND_PARTY_INFO, VariableSet.create()
                .addDescribed("party",party));
    }
}
