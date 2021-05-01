package net.pretronic.dkfriends.common.party;

import net.pretronic.dkfriends.api.event.party.PartyCreateEvent;
import net.pretronic.dkfriends.api.event.party.PartyDeleteEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyManager;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.libraries.utility.Iterators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.function.Predicate;

public class DefaultPartyManager implements PartyManager {

    private final DefaultDKFriends dkFriends;
    private Collection<Party> parties;

    public DefaultPartyManager(DefaultDKFriends dkFriends) {
        this.dkFriends = dkFriends;
    }

    @Override
    public Collection<Party> getParties() {
        return Collections.unmodifiableCollection(getOrLoadParties());
    }

    @Override
    public Collection<Party> getPublicParties() {
        return Collections.unmodifiableCollection(Iterators.filter(getOrLoadParties(), Party::isPublic));
    }

    @Override
    public Party getParty(UUID partyId) {
        return Iterators.findOne(getOrLoadParties(), party -> party.getId().equals(partyId));
    }

    @Override
    public Party getPartyForPlayer(UUID playerId) {
        return Iterators.findOne(getOrLoadParties(), party -> party.isMember(playerId));
    }

    @Override
    public PartyMember getPartyForPlayerAsMember(UUID playerId) {
        for (Party party : getOrLoadParties()) {
            PartyMember member = party.getMember(playerId);
            if(member != null) return member;
        }
        return null;
    }

    @Override
    public boolean isInParty(UUID playerId) {
        return getPartyForPlayer(playerId) != null;
    }

    @Override
    public Party createParty(UUID ownerId) {
        if(isInParty(ownerId)) throw new IllegalArgumentException("Player is already in a party");
        Party party = new DefaultParty(dkFriends,UUID.randomUUID(),ownerId);

        PartyCreateEvent event = null;
        dkFriends.getEventBus().callEvent(PartyCreateEvent.class,event);
        if(event.isCancelled()) return null;

        this.parties.add(party);
        //@Todo storage

        return party;
    }

    @Override
    public void deleteParty(UUID partyId) {
        deleteParty(getParty(partyId));
    }

    @Override
    public void deleteParty(Party party) {
        this.parties.remove(party);

        PartyDeleteEvent event = null;
        dkFriends.getEventBus().callEvent(PartyDeleteEvent.class,event);
        if(event.isCancelled()) return;

        dkFriends.getStorage().getParties().delete()
                .where("PartyId",party.getId())
                .execute();

        this.dkFriends.getStorage().getParties().delete().where("Id",party.getId()).execute();
        this.parties.remove(event);

    }

    private Collection<Party> getOrLoadParties(){
        if(this.parties == null){
            this.parties = new ArrayList<>();
        }
        return this.parties;
    }
}
