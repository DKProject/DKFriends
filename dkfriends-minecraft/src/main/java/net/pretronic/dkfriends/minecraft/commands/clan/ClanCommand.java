package net.pretronic.dkfriends.minecraft.commands.clan;

import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.NotFindable;
import net.pretronic.libraries.command.command.MainCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;

public class ClanCommand extends MainCommand implements NotFindable {

    public ClanCommand(DefaultDKFriends dkFriends, ObjectOwner owner, CommandConfiguration configuration) {
        super(owner, configuration);

        registerCommand(new CreateCommand(dkFriends, owner));
        registerCommand(new InfoCommand(dkFriends, owner));
        registerCommand(new InviteCommand(dkFriends, owner));
        registerCommand(new JumpCommand(owner));
        registerCommand(new LeaveCommand(owner));
        registerCommand(new MessageCommand(owner));
        registerCommand(new PartyCommand(owner));
        registerCommand(new UserInfoCommand(owner));
        registerCommand(new DeleteCommand(owner, dkFriends));
        registerCommand(new PromoteCommand(owner));
        registerCommand(new DemoteCommand(owner));
        registerCommand(new KickCommand(owner));
        registerCommand(new RenameCommand(owner, dkFriends));
        registerCommand(new RetagCommand(owner, dkFriends));
        registerCommand(new AcceptCommand(owner));
        registerCommand(new DenyCommand(owner));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(CommandUtil.isPlayerCheck(sender, Messages.PREFIX_CLAN)){
            super.execute(sender, args);
        }
    }

    @Override
    public void commandNotFound(CommandSender commandSender, String s, String[] strings) {
        commandSender.sendMessage(Messages.COMMAND_CLAN_HELP);
    }
}
