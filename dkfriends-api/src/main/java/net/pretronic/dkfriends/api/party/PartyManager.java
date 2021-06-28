package net.pretronic.dkfriends.api.party;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.Collection;
import java.util.UUID;

public interface PartyManager {

    Collection<Party> getParties();

    Collection<Party> getPublicParties();


    Party getParty(UUID partyId);

    Party getPartyForPlayer(UUID playerId);

    PartyMember getPartyForPlayerAsMember(UUID playerId);

    boolean isInParty(UUID playerId);


    Party createParty(UUID ownerId);

    Party createParty(DKFriendsPlayer owner);

    void deleteParty(UUID partyId);

    void deleteParty(Party party);


}
