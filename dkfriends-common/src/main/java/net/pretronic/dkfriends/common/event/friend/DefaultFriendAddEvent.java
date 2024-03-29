package net.pretronic.dkfriends.common.event.friend;

import net.pretronic.dkfriends.api.event.friend.FriendAddEvent;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.Friend;

import java.util.UUID;

public class DefaultFriendAddEvent implements FriendAddEvent {

    private final Friend friend;
    private transient boolean cancelled;

    public DefaultFriendAddEvent(Friend friend) {
        this.friend = friend;
        this.cancelled = false;
    }

    @Override
    public UUID getPlayerId() {
        return friend.getPlayerId();
    }

    @Override
    public DKFriendsPlayer getPlayer() {
        return friend.getPlayer();
    }

    @Override
    public Friend getFriend() {
        return friend;
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
