package net.pretronic.dkfriends.api.player.friend;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public interface FriendRequest {



    UUID getReceiverId();

    DKFriendsPlayer getReceiver();


    UUID getRequesterId();

    DKFriendsPlayer getRequester();

    default boolean hasMessage(){
        return getMessage() != null;
    }

    String getMessage();

    long getRequestTime();

}
