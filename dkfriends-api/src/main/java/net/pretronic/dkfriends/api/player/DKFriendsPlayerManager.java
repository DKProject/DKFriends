package net.pretronic.dkfriends.api.player;

import java.util.UUID;

public interface DKFriendsPlayerManager {

    DKFriendsPlayer getPlayer(UUID uniqueId);

    DKFriendsPlayer getPlayer(String name);

}
