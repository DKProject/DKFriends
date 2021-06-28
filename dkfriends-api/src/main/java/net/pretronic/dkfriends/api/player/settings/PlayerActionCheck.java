package net.pretronic.dkfriends.api.player.settings;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

public interface PlayerActionCheck {

    boolean matches(DKFriendsPlayer player, DKFriendsPlayer target);

}
