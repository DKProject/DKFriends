package net.pretronic.dkfriends.api.event.friend;

import net.pretronic.dkfriends.api.event.DKFriendsPlayerEvent;
import net.pretronic.dkfriends.api.player.friend.Friend;
import net.pretronic.dkfriends.api.player.friend.FriendRequest;
import net.pretronic.libraries.event.Cancellable;

public interface FriendRemoveEvent extends DKFriendsPlayerEvent, Cancellable {

    Friend getFriend();

}
