package net.pretronic.dkfriends.api.player.settings.checks;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.Friend;
import net.pretronic.dkfriends.api.player.settings.PlayerActionCheck;

public class FavoritePlayerActionCheck implements PlayerActionCheck {

    @Override
    public boolean matches(DKFriendsPlayer player, DKFriendsPlayer target) {
        Friend friend = player.getFriend(target.getId());
        return friend != null && friend.isFavorite();
    }
}
