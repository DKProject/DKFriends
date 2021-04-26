package net.pretronic.dkfriends.minecraft.commands.friend;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.Friend;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.GeneralUtil;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.player.MinecraftPlayer;

import java.util.ArrayList;
import java.util.List;

public class RequestsCommand extends BasicCommand {

    public RequestsCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("requests"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        List<FriendRequest> requests = new ArrayList<>(player.getFriendRequests());
        if(requests.size() == 0){
            sender.sendMessage(Messages.ERROR_FRIEND_REQUEST_EMPTY,VariableSet.create());
            return;
        }

        int page = 1;
        if(arguments.length > 0 && GeneralUtil.isNaturalNumber(arguments[0])){
            page = Integer.parseInt(arguments[0]);
        }

        int from = 10 * (page - 1);
        int to = (page * 10);

        if(requests.size() < from){
            sender.sendMessage(Messages.ERROR_NO_PAGE, VariableSet.create()
                    .add("prefix",Messages.PREFIX)
                    .add("page",page));
            return;
        }

        if(requests.size() <= to) to = requests.size();
        List<FriendRequest> visibleRequests = requests.subList(from,to);

        sender.sendMessage(Messages.COMMAND_FRIEND_REQUESTS, VariableSet.create()
                .add("page",page)
                .addDescribed("requests",visibleRequests));
    }
}
