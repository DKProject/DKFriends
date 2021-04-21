package net.pretronic.dkfriends.player;

import java.util.UUID;

public interface DKFriendsPlayerManager {

    DKFriendsPlayer getPlayer(UUID uniqueId);
    
    DKFriendsPlayer getPlayer(String name);

}
