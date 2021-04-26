package net.pretronic.dkfriends.common.event.friend.request;

import net.pretronic.dkfriends.api.event.friend.request.FriendRequestAcceptEvent;
import net.pretronic.dkfriends.api.event.friend.request.FriendRequestDenyEvent;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;

import java.util.UUID;

public class DefaultFriendRequestAcceptEvent implements FriendRequestAcceptEvent {

    private final DKFriendsPlayer player;
    private final FriendRequest request;

    public DefaultFriendRequestAcceptEvent(DKFriendsPlayer player, FriendRequest request) {
        this.player = player;
        this.request = request;
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
}
