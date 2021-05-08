package net.pretronic.dkfriends.minecraft.commands.clan;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanRole;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

public class DeleteCommand extends BasicCommand {

    private final DefaultDKFriends dkFriends;

    public DeleteCommand(ObjectOwner owner, DefaultDKFriends dkFriends) {
        super(owner, CommandConfiguration.name("delete"));
        this.dkFriends = dkFriends;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        DKFriendsPlayer player = ((MinecraftPlayer)commandSender).getAs(DKFriendsPlayer.class);
        if(CommandUtil.isInClanCheck(commandSender, player)) {
            if(player.getClanMember().getRole() == ClanRole.LEADER) {
                Clan clan = player.getClan();
                boolean deleted = this.dkFriends.getClanManager().deleteClan(clan);
                if(deleted) {
                    commandSender.sendMessage(Messages.COMMAND_CLAN_DELETE);
                } else {
                    //@Todo cancelled
                }
            } else {
                commandSender.sendMessage(Messages.ERROR_CLAN_MEMBER_NOT_ALLOWED);
            }
        }
    }
}
