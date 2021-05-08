package net.pretronic.dkfriends.minecraft.commands.clan;

import net.pretronic.dkfriends.api.clan.Clan;
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

public class InfoCommand extends BasicCommand {

    private final DefaultDKFriends dkFriends;

    public InfoCommand(DefaultDKFriends dkFriends, ObjectOwner owner) {
        super(owner, CommandConfiguration.name("info"));

        this.dkFriends = dkFriends;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(args.length == 1) {
           String nameOrTag = args[0];
            Clan clan = dkFriends.getClanManager().getClan(nameOrTag);
            if(clan == null) clan = dkFriends.getClanManager().getClanByTag(nameOrTag);
            if(clan == null) {
                commandSender.sendMessage(Messages.ERROR_CLAN_NOT_EXISTS, VariableSet.create().add("value", nameOrTag));
                return;
            }
            commandSender.sendMessage(Messages.COMMAND_CLAN_INFO, VariableSet.create().addDescribed("clan", clan));
        } else {
            DKFriendsPlayer player = ((MinecraftPlayer)commandSender).getAs(DKFriendsPlayer.class);
            if(CommandUtil.isInClanCheck(commandSender, player)) {
                commandSender.sendMessage(Messages.COMMAND_CLAN_INFO, VariableSet.create().addDescribed("clan", player.getClan()));
            }
        }
    }
}
