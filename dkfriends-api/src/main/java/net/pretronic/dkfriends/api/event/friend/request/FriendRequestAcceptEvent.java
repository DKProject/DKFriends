package net.pretronic.dkfriends.api.event.friend.request;

import net.pretronic.dkfriends.api.event.DKFriendsPlayerEvent;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;

public interface FriendRequestAcceptEvent extends DKFriendsPlayerEvent {

    FriendRequest getRequest();

}
