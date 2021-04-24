package net.pretronic.dkfriends.minecraft.commands.friend;

import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.MainCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;

public class FriendCommand extends MainCommand {

    public FriendCommand(ObjectOwner owner, CommandConfiguration configuration) {
        super(owner, configuration);

        registerCommand(new ListCommand(owner));
        registerCommand(new AddCommand(owner));
        registerCommand(new RemoveCommand(owner));
        registerCommand(new AcceptCommand(owner));
        registerCommand(new AcceptAllCommand(owner));
        registerCommand(new DenyCommand(owner));
        registerCommand(new DenyAllCommand(owner));
        registerCommand(new ClearCommand(owner));

        registerCommand(new JumpCommand(owner));

        MsgCommand msgCommand = new MsgCommand(owner);
        registerCommand(msgCommand);
        registerCommand(msgCommand);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(CommandUtil.isPlayerCheck(sender, Messages.PREFIX)){
            super.execute(sender, args);
        }
    }
}
