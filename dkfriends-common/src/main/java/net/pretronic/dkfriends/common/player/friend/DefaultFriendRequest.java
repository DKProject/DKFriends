package net.pretronic.dkfriends.common.player.friend;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;

import java.util.UUID;

public class DefaultFriendRequest implements FriendRequest {

    private final DKFriends dkFriends;
    private final UUID receiverId;
    private final UUID requesterId;
    private final String message;
    private final long requestTime;

    private transient DKFriendsPlayer receiver;
    private transient DKFriendsPlayer requester;

    public DefaultFriendRequest(DKFriends dkFriends,UUID receiverId, UUID requesterId, String message, long requestTime) {
        this.dkFriends = dkFriends;
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
        if(receiver == null) receiver = dkFriends.getPlayerManager().getPlayer(receiverId);
        return receiver;
    }

    @Override
    public UUID getRequesterId() {
        return requesterId;
    }

    @Override
    public DKFriendsPlayer getRequester() {
        if(requester == null) requester = dkFriends.getPlayerManager().getPlayer(requesterId);
        return requester;
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
