package net.pretronic.dkfriends.player;

import java.util.UUID;

public interface PlayerBlock {

    UUID getPlayerId();

    UUID getTargetId();

    long getBlockTime();


    void unblock();
}
