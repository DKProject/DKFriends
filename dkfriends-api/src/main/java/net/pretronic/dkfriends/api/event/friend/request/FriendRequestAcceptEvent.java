package net.pretronic.dkfriends.api.event.friend.request;

import net.pretronic.dkfriends.api.event.DKFriendsPlayerEvent;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;
import net.pretronic.libraries.event.network.NetworkEvent;

@NetworkEvent(ignoreNetworkException = true)
public interface FriendRequestAcceptEvent extends DKFriendsPlayerEvent {

    FriendRequest getRequest();

}
