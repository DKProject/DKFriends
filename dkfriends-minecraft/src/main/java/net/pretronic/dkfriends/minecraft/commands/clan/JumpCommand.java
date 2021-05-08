package net.pretronic.dkfriends.minecraft.commands.clan;

import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

public class JumpCommand extends BasicCommand {

    public JumpCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("jump"));
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(args.length != 1) {
            commandSender.sendMessage(Messages.COMMAND_CLAN_HELP);
            return;
        }

        MinecraftPlayer target = CommandUtil.getPlayer(commandSender, Messages.PREFIX_CLAN, args[0]);
        if(target == null) return;


    }
}
