package net.pretronic.dkfriends.common.event.friend;

import net.pretronic.dkfriends.api.event.friend.FriendAddEvent;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.Friend;

import java.util.UUID;

public class DefaultFriendAddEvent implements FriendAddEvent {

    private final DKFriendsPlayer player;
    private final Friend friend;

    public DefaultFriendAddEvent(DKFriendsPlayer player, Friend friend) {
        this.player = player;
        this.friend = friend;

        this.cancelled = false;
    }

    private boolean cancelled;

    @Override
    public UUID getPlayerId() {
        return player.getId();
    }

    @Override
    public DKFriendsPlayer getPlayer() {
        return player;
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
