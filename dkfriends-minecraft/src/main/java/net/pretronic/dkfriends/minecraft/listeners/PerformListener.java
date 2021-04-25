package net.pretronic.dkfriends.minecraft.listeners;

import net.pretronic.dkfriends.api.event.friend.FriendAddEvent;
import net.pretronic.dkfriends.api.event.friend.FriendRemoveEvent;
import net.pretronic.dkfriends.api.event.friend.request.FriendRequestSendEvent;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.event.EventPriority;
import net.pretronic.libraries.event.Listener;
import net.pretronic.libraries.event.network.NetworkListener;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.ConnectedMinecraftPlayer;

public class PerformListener {

    @Listener(priority = EventPriority.HIGHEST)
    @NetworkListener(priority = EventPriority.HIGHEST)
    public void onFriendRequestSend(FriendRequestSendEvent event){
        if(event.isCancelled()) return;
        ConnectedMinecraftPlayer player = McNative.getInstance().getLocal().getConnectedPlayer(event.getRequest().getReceiverId());
        if(player !=  null){
            player.sendMessage(Messages.FRIEND_REQUEST, VariableSet.create().addDescribed("player",event.getRequest().getRequester()));
        }
    }

    @Listener(priority = EventPriority.HIGHEST)
    @NetworkListener(priority = EventPriority.HIGHEST)
    public void onFriendRequestDeny(FriendRequestSendEvent event){
        if(event.isCancelled()) return;
        ConnectedMinecraftPlayer player = McNative.getInstance().getLocal().getConnectedPlayer(event.getRequest().getRequesterId());
        if(player !=  null){
            player.sendMessage(Messages.FRIEND_DENY, VariableSet.create().addDescribed("player",event.getPlayer()));
        }
    }

    @Listener(priority = EventPriority.HIGHEST)
    @NetworkListener(priority = EventPriority.HIGHEST)
    public void onFriendAdd(FriendAddEvent event){
        if(event.isCancelled()) return;
        ConnectedMinecraftPlayer player = McNative.getInstance().getLocal().getConnectedPlayer(event.getPlayerId());
        if(player !=  null){
            player.sendMessage(Messages.FRIEND_ADD, VariableSet.create().addDescribed("friend",event.getPlayer()));
        }

        ConnectedMinecraftPlayer player2 = McNative.getInstance().getLocal().getConnectedPlayer(event.getPlayerId());
        if(player2 !=  null){
            player2.sendMessage(Messages.FRIEND_ADD, VariableSet.create().addDescribed("friend",event.getFriend().getFriend()));
        }
    }

    @Listener(priority = EventPriority.HIGHEST)
    @NetworkListener(priority = EventPriority.HIGHEST)
    public void onFriendRemove(FriendRemoveEvent event){
        if(event.isCancelled()) return;

        ConnectedMinecraftPlayer player = McNative.getInstance().getLocal().getConnectedPlayer(event.getPlayerId());
        if(player !=  null){
            player.sendMessage(Messages.FRIEND_REMOVE, VariableSet.create().addDescribed("friend",event.getFriend().getFriend()));
        }
    }

}
