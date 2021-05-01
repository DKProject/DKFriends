package net.pretronic.dkfriends.minecraft.commands.party;

import net.pretronic.dkfriends.api.party.Party;
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

public class LeaveCommand extends BasicCommand {

    public LeaveCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("leave","l","quiet"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        if(arguments.length < 1){
            //@Todo help message
            return;
        }
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        Party party = player.getParty();
        if(party == null){
            sender.sendMessage(Messages.ERROR_PARTY_NOT);
            return;
        }

        party.leaveMember(player.getId());
    }
}
