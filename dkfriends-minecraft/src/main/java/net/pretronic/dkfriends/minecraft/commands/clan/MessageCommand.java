package net.pretronic.dkfriends.minecraft.commands.clan;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

public class MessageCommand extends BasicCommand {

    public MessageCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("message"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length < 1) {
            sender.sendMessage(Messages.COMMAND_CLAN_HELP);
            return;
        }
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        if(CommandUtil.isInClanCheck(sender, player)) {
            player.getClan().sendMessage(player, CommandUtil.readStringFromArguments(args,0));
        }
    }
}
