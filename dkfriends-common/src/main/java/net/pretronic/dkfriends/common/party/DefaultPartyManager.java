package net.pretronic.dkfriends.common.party;

import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyManager;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.libraries.utility.Iterators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class DefaultPartyManager implements PartyManager {

    private Collection<Party> parties;

    @Override
    public Collection<Party> getParties() {
        return Collections.unmodifiableCollection(getOrLoadParties());
    }

    @Override
    public Collection<Party> getPublicParties() {
        return Iterators.filter(getOrLoadParties(), Party::isPublic);
    }

    @Override
    public Party getParty(UUID partyId) {
        return null;
    }

    @Override
    public Party getPartyForPlayer(UUID playerId) {
        return null;
    }

    @Override
    public PartyMember getPartyForPlayerAsMember(UUID playerId) {
        return null;
    }

    @Override
    public Party createParty(UUID ownerId) {
        return null;
    }

    @Override
    public void deleteParty(UUID partyId) {

    }

    private Collection<Party> getOrLoadParties(){
        if(this.parties == null){
            this.parties = new ArrayList<>();
        }
        return this.parties;
    }
}
