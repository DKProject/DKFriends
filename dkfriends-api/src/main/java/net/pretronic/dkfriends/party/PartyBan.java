package net.pretronic.dkfriends.party;

import java.util.UUID;

public interface PartyBan {

    UUID getPartyId();

    Party getParty();

    UUID getPlayerId();

    UUID getReason();

    long getBanTime();


    void unban();

}
