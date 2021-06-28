package net.pretronic.dkfriends.api.player.settings.checks;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.settings.PlayerActionCheck;

public class FriendPlayerActionCheck implements PlayerActionCheck {

    @Override
    public boolean matches(DKFriendsPlayer player, DKFriendsPlayer target) {
        return player.isFriend(target.getId());
    }
}
