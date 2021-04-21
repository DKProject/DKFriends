package net.pretronic.dkfriends.api.party;

import java.util.UUID;

public interface PartyInvitation {

    UUID getPartyId();

    Party getParty();

    UUID getPlayerId();

    UUID getInviterId();

    long getInvitationTime();


    PartyMember join();

}
