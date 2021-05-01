package net.pretronic.dkfriends.minecraft.commands.party;

import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.network.component.server.MinecraftServer;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.player.OnlineMinecraftPlayer;

public class JumpCommand extends BasicCommand {

    public JumpCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("message","m"));
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

        PartyMember leader = party.getLeader();

        OnlineMinecraftPlayer target = McNative.getInstance().getNetwork().getOnlinePlayer(leader.getPlayerId());

        MinecraftServer server = target.getServer();
        if(((OnlineMinecraftPlayer)sender).getServer().equals(server)){

        }else{
     //       sender.sendMessage(Messages.);
            target.connect(server);
        }
    }
}
