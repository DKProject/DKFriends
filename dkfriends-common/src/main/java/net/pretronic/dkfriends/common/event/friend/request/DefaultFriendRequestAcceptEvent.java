package net.pretronic.dkfriends.common.event.friend.request;

import net.pretronic.dkfriends.api.event.friend.request.FriendRequestAcceptEvent;
import net.pretronic.dkfriends.api.event.friend.request.FriendRequestDenyEvent;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.libraries.event.injection.annotations.Inject;

import java.util.UUID;

public class DefaultFriendRequestAcceptEvent implements FriendRequestAcceptEvent {

    private final FriendRequest request;

    public DefaultFriendRequestAcceptEvent(FriendRequest request) {
        this.request = request;
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
}
