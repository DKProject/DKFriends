package net.pretronic.dkfriends.api.party;

import java.util.Collection;
import java.util.UUID;

public interface PartyManager {

    Collection<Party> getParties();

    Collection<Party> getPublicParties();


    Party getParty(UUID partyId);

    Party getPartyForPlayer(UUID playerId);


    Party createParty(UUID ownerId);

    void deleteParty(UUID partyId);


}
