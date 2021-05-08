package net.pretronic.dkfriends.minecraft.commands.clan;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

public class UserInfoCommand extends BasicCommand {

    public UserInfoCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("userinfo"));
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(args.length != 1) {
            commandSender.sendMessage(Messages.COMMAND_CLAN_HELP);
            return;
        }
        MinecraftPlayer target = CommandUtil.getPlayer(commandSender, Messages.PREFIX_CLAN, args[0]);
        if(target != null) {
            DKFriendsPlayer targetFriendsPlayer = target.getAs(DKFriendsPlayer.class);
            if(CommandUtil.isInClanCheck(commandSender, targetFriendsPlayer)) {
                commandSender.sendMessage(Messages.COMMAND_CLAN_INFO, VariableSet.create().addDescribed("clan", targetFriendsPlayer.getClan()));
            }
        }
    }
}
