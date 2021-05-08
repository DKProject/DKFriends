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
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

public class RenameCommand extends BasicCommand {

    private final DefaultDKFriends dkFriends;

    public RenameCommand(ObjectOwner owner, DefaultDKFriends dkFriends) {
        super(owner, CommandConfiguration.name("rename"));
        this.dkFriends = dkFriends;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(args.length != 1) {
            commandSender.sendMessage(Messages.COMMAND_CLAN_HELP);
            return;
        }

        DKFriendsPlayer player = ((MinecraftPlayer)commandSender).getAs(DKFriendsPlayer.class);
        if(CommandUtil.isInClanCheck(commandSender, player)) {
            if(player.getClanMember().getRole() == ClanRole.LEADER) {
                Clan clan = player.getClan();
                String newName = args[0];

                if(dkFriends.getClanManager().getClan(newName) != null) {
                    commandSender.sendMessage(Messages.ERROR_CLAN_NAME_ALREADY_EXISTS, VariableSet.create().add("name", newName));
                    return;
                }

                boolean renamed = clan.setName(newName);
                if(renamed) {
                    commandSender.sendMessage(Messages.COMMAND_CLAN_RENAME, VariableSet.create().addDescribed("clan", clan));
                } else {
                    //@Todo cancelled
                }
            } else {
                commandSender.sendMessage(Messages.ERROR_CLAN_MEMBER_NOT_ALLOWED);
            }
        }
    }
}
