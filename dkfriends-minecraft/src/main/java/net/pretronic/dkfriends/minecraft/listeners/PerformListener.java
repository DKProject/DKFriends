package net.pretronic.dkfriends.minecraft.listeners;

import net.pretronic.dkfriends.api.event.friend.request.FriendRequestSendEvent;
import net.pretronic.libraries.event.Listener;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.MinecraftPlayer;

public class PerformListener {

    @Listener
    public void onFriendRequestSend(FriendRequestSendEvent event){
        MinecraftPlayer player = McNative.getInstance().getLocal().getConnectedPlayer(event.getRequest().getReceiverId());
        if(player !=  null){
            //@Todo send message
        }
    }

}
