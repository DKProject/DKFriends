package net.pretronic.dkfriends.minecraft.commands.clan;

import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.clan.ClanRole;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.DefaultDKFriends;
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

public class InviteCommand extends BasicCommand implements Completable {

    private final DefaultDKFriends dkFriends;

    public InviteCommand(DefaultDKFriends dkFriends, ObjectOwner owner) {
        super(owner, CommandConfiguration.name("invite"));
        this.dkFriends = dkFriends;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(args.length != 1) {
            commandSender.sendMessage(Messages.COMMAND_CLAN_HELP);
            return;
        }

        DKFriendsPlayer friendsPlayer = ((MinecraftPlayer)commandSender).getAs(DKFriendsPlayer.class);

        ClanMember inviter = friendsPlayer.getClanMember();
        if(inviter == null) {
            commandSender.sendMessage(Messages.ERROR_CLAN_NOT_IN_CLAN);
            return;
        }
        if(inviter.getRole() == ClanRole.GUEST) {
            commandSender.sendMessage(Messages.ERROR_CLAN_MEMBER_NOT_ALLOWED);
            return;
        }

        MinecraftPlayer target = CommandUtil.getPlayer(commandSender, Messages.PREFIX_CLAN, args[0]);
        if(target == null) return;

        DKFriendsPlayer targetFriendsPlayer = target.getAs(DKFriendsPlayer.class);
        if(targetFriendsPlayer.getClan() != null || inviter.getClan().equals(targetFriendsPlayer.getClan())) {
            commandSender.sendMessage(Messages.ERROR_CLAN_ALREADY_IN_CLAN_TARGET, VariableSet.create()
                    .add("target", target));
            return;
        }

        if(inviter.getClan().hasInvitation(targetFriendsPlayer.getId())) {
            commandSender.sendMessage(Messages.ERROR_CLAN_MEMBER_ALREADY_INVITED, VariableSet.create()
                    .addDescribed("target", targetFriendsPlayer));
            return;
        }

        ClanInvitation invitation = inviter.getClan().sendInvitation(friendsPlayer, targetFriendsPlayer.getId());
        commandSender.sendMessage(Messages.COMMAND_CLAN_INVITE, VariableSet.create()
                .addDescribed("target", targetFriendsPlayer)
                .addDescribed("invitation", invitation));
    }

    @Override
    public Collection<String> complete(CommandSender sender, String[] args) {
        return CommandUtil.completeOnlinePlayer(sender,args);
    }
}
