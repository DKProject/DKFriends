package net.pretronic.dkfriends.api.event.clan;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.libraries.event.Cancellable;
import net.pretronic.libraries.event.network.NetworkEvent;

import java.util.UUID;

@NetworkEvent(ignoreNetworkException = true)
public interface ClanMessageEvent extends ClanEvent, Cancellable {

    UUID getSenderId();

    DKFriendsPlayer getSender();

    String getChannel();

    void setChannel(String channel);


    String getMessage();

    void setMessage(String message);
}
