package net.pretronic.dkfriends.player.friend;

import java.util.UUID;

public interface FriendRequest {

    UUID getPlayerId();

    UUID getTargetId();

    String getMessage();

    long getRequestTime();

}
