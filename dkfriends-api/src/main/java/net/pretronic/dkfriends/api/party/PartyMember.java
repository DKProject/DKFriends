package net.pretronic.dkfriends.api.party;

import java.util.UUID;

public interface PartyMember {

    UUID getPartyId();

    Party getParty();

    UUID getPlayerId();

    long getJoinTime();


    PartyRole getRole();

    void setRole(PartyRole role);

    void promote();

    void demote();


    void leave();

}
