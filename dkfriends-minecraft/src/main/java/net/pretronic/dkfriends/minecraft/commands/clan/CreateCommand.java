package net.pretronic.dkfriends.minecraft.commands.clan;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanRole;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

public class CreateCommand extends BasicCommand {

    private final DefaultDKFriends dkFriends;

    public CreateCommand(DefaultDKFriends dkFriends, ObjectOwner owner) {
        super(owner, CommandConfiguration.name("create"));
        this.dkFriends = dkFriends;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(args.length != 2) {
            commandSender.sendMessage(Messages.COMMAND_CLAN_HELP);
            return;
        }
        MinecraftPlayer player = (MinecraftPlayer) commandSender;

        DKFriendsPlayer friendsPlayer = player.getAs(DKFriendsPlayer.class);

        if(friendsPlayer.getClan() != null) {
            commandSender.sendMessage(Messages.ERROR_CLAN_ALREADY_IN_CLAN_SELF);
            return;
        }

        String name = args[0];
        String tag = args[1];

        Clan clan = this.dkFriends.getClanManager().createClan(name, tag);

        if(clan == null) {
            commandSender.sendMessage(Messages.COMMAND_CLAN_CREATE_ALREADY_EXISTS, VariableSet.create()
                    .add("name", name)
                    .add("tag", tag));
            return;
        }
        clan.addMember(player.getUniqueId(), ClanRole.LEADER);

        commandSender.sendMessage(Messages.COMMAND_CLAN_CREATE, VariableSet.create().addDescribed("clan", clan));
    }
}
