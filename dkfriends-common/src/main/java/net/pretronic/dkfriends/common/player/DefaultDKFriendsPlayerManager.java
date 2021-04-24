package net.pretronic.dkfriends.common.player;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.DKFriendsPlayerManager;
import net.pretronic.dkfriends.common.DefaultDKFriends;

import java.util.UUID;

public class DefaultDKFriendsPlayerManager implements DKFriendsPlayerManager {

    private final DefaultDKFriends dkFriends;

    public DefaultDKFriendsPlayerManager(DefaultDKFriends dkFriends) {
        this.dkFriends = dkFriends;
    }

    @Override
    public DKFriendsPlayer getPlayer(UUID uniqueId) {
        return new DefaultDKFriendsPlayer(dkFriends,uniqueId);
    }

    @Override
    public DKFriendsPlayer getLoadedPlayer(UUID uniqueId) {
        return null;
    }
}
