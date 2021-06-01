package net.pretronic.dkfriends.common.player.friend;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;
import net.pretronic.libraries.event.injection.annotations.Inject;

import java.util.UUID;

public class DefaultFriendRequest implements FriendRequest {

    @Inject
    private final DKFriends dkfriends;

    private final UUID receiverId;
    private final UUID requesterId;
    private final String message;
    private final long requestTime;

    private transient DKFriendsPlayer receiver;
    private transient DKFriendsPlayer requester;

    public DefaultFriendRequest(DKFriends dkfriends,DKFriendsPlayer receiver, UUID requesterId, String message, long requestTime) {
        this.dkfriends = dkfriends;
        this.receiverId = receiver.getId();
        this.requesterId = requesterId;
        this.message = message;
        this.requestTime = requestTime;

        this.receiver = receiver;
    }

    @Override
    public UUID getReceiverId() {
        return receiverId;
    }

    @Override
    public DKFriendsPlayer getReceiver() {
        if(receiver == null) receiver = dkfriends.getPlayerManager().getPlayer(receiverId);
        return receiver;
    }

    @Override
    public UUID getRequesterId() {
        return requesterId;
    }

    @Override
    public DKFriendsPlayer getRequester() {
        if(requester == null) requester = dkfriends.getPlayerManager().getPlayer(requesterId);
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
