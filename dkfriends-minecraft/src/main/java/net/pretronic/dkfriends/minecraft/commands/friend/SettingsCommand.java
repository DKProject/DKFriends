package net.pretronic.dkfriends.minecraft.commands.friend;

import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;

public class SettingsCommand extends BasicCommand {

    public SettingsCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("settings","setting","s"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        if(arguments.length < 1){
            sender.sendMessage(Messages.COMMAND_FRIEND_HELP);
            return;
        }
        String setting = arguments[0];
        String value = arguments[1];
    }
}
