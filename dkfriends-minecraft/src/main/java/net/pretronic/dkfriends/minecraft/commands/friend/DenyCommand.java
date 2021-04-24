package net.pretronic.dkfriends.minecraft.commands.friend;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

public class DenyCommand extends BasicCommand {

    public DenyCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("deny"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        if(arguments.length < 1){
            //@Todo help message => Bessere Lösung
            return;
        }
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        MinecraftPlayer target = CommandUtil.getPlayer(sender, Messages.PREFIX,arguments[0]);
        if(target == null) return;

        FriendRequest request = player.getFriendRequest(target.getUniqueId());

        if(request == null){
            sender.sendMessage(Messages.ERROR_FRIEND_REQUEST_NOT, VariableSet.create()
                    .addDescribed("player",target));
            return;
        }

        player.denyFriendRequest(request);
    }
}
