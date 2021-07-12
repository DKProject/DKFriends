package net.pretronic.dkfriends.minecraft.commands.party;

import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.settings.PlayerSettings;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.Completable;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class InviteCommand extends BasicCommand implements Completable {

    public InviteCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("invite","i"));
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

        if(!target.isOnline()){
            sender.sendMessage(Messages.ERROR_PLAYER_NOT_ONLINE, VariableSet.create()
                    .addDescribed("player",target)
                    .addDescribed("prefix",Messages.PREFIX_PARTY));
            return;
        }

        DKFriendsPlayer targetFriend = target.getAs(DKFriendsPlayer.class);
        if(!targetFriend.isActionAllow(PlayerSettings.PARTY_ALLOW_INVITATIONS,player)){
            sender.sendMessage(Messages.COMMAND_PARTY_INVITE_NOT_ALLOWED, VariableSet.create()
                    .addDescribed("player",target));
            return;
        }

        Party party = player.getParty();
        if(party != null){
            if(!party.canInteract(player.getId(),target.getUniqueId())){
                sender.sendMessage(Messages.ERROR_PARTY_NOT_ALLOWED);
                return;
            }
        }else party = player.createParty();

        if(party == null) return;

        party.invite(player,target.getUniqueId());
        sender.sendMessage(Messages.COMMAND_PARTY_INVITED,VariableSet.create()
                .addDescribed("player",target));
    }

    @Override
    public Collection<String> complete(CommandSender sender, String[] args) {
        Collection<String> result = new HashSet<>();
        result.addAll(CommandUtil.completeOnlinePlayer(sender,args));
        result.addAll(CommandUtil.completeOnlineFriends(sender,args));
        return result;
    }
}
