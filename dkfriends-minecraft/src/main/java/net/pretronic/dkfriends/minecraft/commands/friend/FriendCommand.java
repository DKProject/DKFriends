package net.pretronic.dkfriends.minecraft.commands.friend;

import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.MainCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.McNative;

public class FriendCommand extends MainCommand {

    public FriendCommand(ObjectOwner owner, CommandConfiguration configuration) {
        super(owner, configuration);

        registerCommand(new ListCommand(owner));
        registerCommand(new AddCommand(owner));
        registerCommand(new RemoveCommand(owner));
        registerCommand(new RequestsCommand(owner));
        registerCommand(new AcceptCommand(owner));
        registerCommand(new AcceptAllCommand(owner));
        registerCommand(new DenyCommand(owner));
        registerCommand(new DenyAllCommand(owner));
        registerCommand(new ClearCommand(owner));

        registerCommand(new PartyCommand(owner));
        registerCommand(new MessageCommand(owner));
        registerCommand(new FavoriteCommand(owner));

        if(McNative.getInstance().isNetworkAvailable()){
            registerCommand(new JumpCommand(owner));
        }
    }

    /*
    Favorite
    Message
    Respond

    settings requests
    settings notify
    settings jump
    settings status
     */

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(CommandUtil.isPlayerCheck(sender, Messages.PREFIX_FRIEND)){
            super.execute(sender, args);
        }
    }
}
