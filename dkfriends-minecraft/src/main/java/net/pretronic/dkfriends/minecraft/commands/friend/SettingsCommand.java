package net.pretronic.dkfriends.minecraft.commands.friend;

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
        if(arguments.length < 2){
            //@Todo help message
            return;
        }
        String setting = arguments[0];
        String value = arguments[1];
    }
}
