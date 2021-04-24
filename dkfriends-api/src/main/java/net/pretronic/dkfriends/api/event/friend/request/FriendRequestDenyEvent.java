package net.pretronic.dkfriends.api.event.friend.request;

import net.pretronic.dkfriends.api.event.DKFriendsPlayerEvent;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;

public interface FriendRequestDenyEvent extends DKFriendsPlayerEvent {

    FriendRequest getRequest();

}
