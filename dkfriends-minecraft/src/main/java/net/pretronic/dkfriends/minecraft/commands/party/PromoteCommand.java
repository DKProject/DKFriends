package net.pretronic.dkfriends.minecraft.commands.party;

import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.party.PartyRole;
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

public class PromoteCommand extends BasicCommand implements Completable {

    public PromoteCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("promote","p"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        if(arguments.length < 1){
            sender.sendMessage(Messages.COMMAND_PARTY_HELP);
            return;
        }
        if(CommandUtil.isSelfCheck(Messages.PREFIX_PARTY,sender,arguments[0])) return;
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        MinecraftPlayer target = CommandUtil.getPlayer(sender,Messages.PREFIX_FRIEND,arguments[0]);
        if(target == null) return;

        Party party = player.getParty();
        if(party == null){
            sender.sendMessage(Messages.ERROR_PARTY_NOT);
            return;
        }

        PartyMember member = party.getMember(target.getUniqueId());
        if(member == null){
            sender.sendMessage(Messages.ERROR_PARTY_NOT_MEMBER,VariableSet.create()
                    .addDescribed("player",target));
            return;
        }

        PartyMember own = party.getMember(player.getId());
        if(own.getRole() != PartyRole.LEADER){
            sender.sendMessage(Messages.ERROR_PARTY_NOT_ALLOWED);
            return;
        }

        member.promote();
    }

    @Override
    public Collection<String> complete(CommandSender sender, String[] args) {
        return CommandUtil.completePartyMembers(sender,args);
    }
}
