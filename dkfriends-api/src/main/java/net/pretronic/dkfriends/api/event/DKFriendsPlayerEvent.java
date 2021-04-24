package net.pretronic.dkfriends.api.event;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

public interface DKFriendsPlayerEvent extends DKFriendsEvent{

    DKFriendsPlayer getPlayer();

}
