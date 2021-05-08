package net.pretronic.dkfriends.minecraft.commands.clan;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

public class KickCommand extends BasicCommand {

    public KickCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("kick"));
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(args.length != 1) {
            commandSender.sendMessage(Messages.COMMAND_CLAN_HELP);
            return;
        }
        DKFriendsPlayer player = ((MinecraftPlayer)commandSender).getAs(DKFriendsPlayer.class);
        if(CommandUtil.isInClanCheck(commandSender, player)) {
            String targetName = args[0];
            MinecraftPlayer targetMinecraftPlayer = CommandUtil.getPlayer(commandSender, Messages.PREFIX_CLAN, targetName);

            if(targetMinecraftPlayer != null) {
                Clan clan = player.getClan();
                if(!CommandUtil.isInSameClan(commandSender, clan, targetMinecraftPlayer)) return;

                ClanMember member = player.getClanMember();
                ClanMember targetClanMember = clan.getMember(targetMinecraftPlayer.getUniqueId());

                if(targetClanMember.canKick(member)) {
                    targetClanMember.kick(member);
                    commandSender.sendMessage(Messages.COMMAND_CLAN_KICK, VariableSet.create().addDescribed("member", targetClanMember));
                } else {
                    commandSender.sendMessage(Messages.ERROR_CLAN_MEMBER_NOT_ALLOWED);
                }
            }
        }
    }
}
