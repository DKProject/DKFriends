package net.pretronic.dkfriends.api.event;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public interface DKFriendsPlayerEvent extends DKFriendsEvent{

    UUID getPlayerId();

    DKFriendsPlayer getPlayer();

}
