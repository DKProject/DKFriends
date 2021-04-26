package net.pretronic.dkfriends.common.event.friend.request;

import net.pretronic.dkfriends.api.event.friend.request.FriendRequestSendEvent;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;

import java.util.UUID;

public class DefaultFriendRequestSendEvent implements FriendRequestSendEvent {

    private final DKFriendsPlayer player;
    private final FriendRequest request;

    private boolean cancelled;

    public DefaultFriendRequestSendEvent(DKFriendsPlayer player, FriendRequest request) {
        this.player = player;
        this.request = request;

        this.cancelled = false;
    }

    @Override
    public UUID getPlayerId() {
        return player.getId();
    }

    @Override
    public DKFriendsPlayer getPlayer() {
        return player;
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
