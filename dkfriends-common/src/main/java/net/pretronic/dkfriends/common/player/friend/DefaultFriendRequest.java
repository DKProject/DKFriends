package net.pretronic.dkfriends.common.player.friend;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;

import java.util.UUID;

public class DefaultFriendRequest implements FriendRequest {

    private final UUID receiverId;
    private final UUID requesterId;
    private final String message;
    private final long requestTime;

    public DefaultFriendRequest(UUID receiverId, UUID requesterId, String message, long requestTime) {
        this.receiverId = receiverId;
        this.requesterId = requesterId;
        this.message = message;
        this.requestTime = requestTime;
    }

    @Override
    public UUID getReceiverId() {
        return receiverId;
    }

    @Override
    public DKFriendsPlayer getReceiver() {
        throw new UnsupportedOperationException();
    }

    @Override
    public UUID getRequesterId() {
        return requesterId;
    }

    @Override
    public DKFriendsPlayer getRequester() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public long getRequestTime() {
        return requestTime;
    }
}
