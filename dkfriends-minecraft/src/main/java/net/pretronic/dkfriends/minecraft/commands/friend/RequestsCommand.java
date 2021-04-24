package net.pretronic.dkfriends.minecraft.commands.friend;

import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;

public class RequestsCommand extends BasicCommand {

    public RequestsCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("jump","j"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {

    }
}
