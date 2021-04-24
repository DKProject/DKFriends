package net.pretronic.dkfriends.minecraft.commands.friend;

import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.Command;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;

public class RespondCommand extends BasicCommand {

    //private final Command msgCommand;

    public RespondCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("respond","r"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
       // msgCommand.execute(sender,arguments);
    }
}
