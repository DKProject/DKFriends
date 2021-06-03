package net.pretronic.dkfriends.common.event.friend.request;

import net.pretronic.dkfriends.api.event.friend.request.FriendRequestSendEvent;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.libraries.event.injection.annotations.Inject;

import java.util.UUID;

public class DefaultFriendRequestSendEvent implements FriendRequestSendEvent {

    private final FriendRequest request;
    private transient boolean cancelled;

    public DefaultFriendRequestSendEvent(FriendRequest request) {
        this.request = request;
        this.cancelled = false;
    }

    @Override
    public UUID getPlayerId() {
        return request.getReceiverId();
    }

    @Override
    public DKFriendsPlayer getPlayer() {
        return request.getReceiver();
    }

    @Override
    public FriendRequest getRequest() {
        return request;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
