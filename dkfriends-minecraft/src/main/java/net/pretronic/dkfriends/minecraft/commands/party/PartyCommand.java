package net.pretronic.dkfriends.minecraft.commands.party;

import net.pretronic.dkfriends.api.party.PartyManager;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.NotFindable;
import net.pretronic.libraries.command.command.MainCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;

public class PartyCommand extends MainCommand implements NotFindable {

    public PartyCommand(ObjectOwner owner, CommandConfiguration configuration,PartyManager partyManager) {
        super(owner, configuration);

        registerCommand(new ListCommand(owner,partyManager));
        registerCommand(new CreateCommand(owner));
        registerCommand(new DeleteCommand(owner));
        registerCommand(new InviteCommand(owner));
        registerCommand(new LeaveCommand(owner));
        registerCommand(new AcceptCommand(owner,partyManager));
        registerCommand(new DenyCommand(owner,partyManager));
        registerCommand(new InfoCommand(owner));
        registerCommand(new PromoteCommand(owner));
        registerCommand(new DemoteCommand(owner));
        registerCommand(new KickCommand(owner));

        registerCommand(new PublicCommand(owner));

        registerCommand(new MessageCommand(owner));
        registerCommand(new JumpCommand(owner));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(CommandUtil.isPlayerCheck(sender, Messages.PREFIX_FRIEND)){
            super.execute(sender, args);
        }
    }

    @Override
    public void commandNotFound(CommandSender sender, String s, String[] strings) {
        sender.sendMessage(Messages.COMMAND_PARTY_HELP);
    }
}
