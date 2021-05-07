package net.pretronic.dkfriends.api.party;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public interface PartyMember {

    UUID getPartyId();

    Party getParty();


    UUID getPlayerId();

    DKFriendsPlayer getPlayer();

    long getJoinTime();


    PartyRole getRole();

    void setRole(PartyRole role);

    void promote();

    void demote();


    void leave();

}
