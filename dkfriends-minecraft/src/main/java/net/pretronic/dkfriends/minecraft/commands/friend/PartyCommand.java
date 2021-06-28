package net.pretronic.dkfriends.minecraft.commands.friend;

import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.Completable;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.network.component.server.MinecraftServer;
import org.mcnative.runtime.api.player.ConnectedMinecraftPlayer;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.player.OnlineMinecraftPlayer;

import java.util.Collection;

public class PartyCommand extends BasicCommand{

    public PartyCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("party","p"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        if(arguments.length < 1){
            sender.sendMessage(Messages.COMMAND_FRIEND_HELP);
            return;
        }
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        Party party;
        if(player.isInParty()){
            party = player.getParty();
            //@Todo check if permissions to invite
        }else party = player.createParty();

        boolean no = true;
        for (OnlineMinecraftPlayer online : McNative.getInstance().getNetwork().getOnlinePlayers()) {
            if(player.isFriend(online.getUniqueId())){
                no= true;
                party.invite(player,online.getUniqueId());
            }
        }

        if(no){
            //@Todo send message
        }
    }
}
