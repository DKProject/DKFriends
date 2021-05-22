package net.pretronic.dkfriends.common.party;

import net.pretronic.databasequery.api.collection.DatabaseCollection;
import net.pretronic.databasequery.api.query.SearchOrder;
import net.pretronic.databasequery.api.query.result.QueryResultEntry;
import net.pretronic.dkfriends.api.event.party.PartyCreateEvent;
import net.pretronic.dkfriends.api.event.party.PartyDeleteEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyManager;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.party.PartyRole;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.common.event.party.DefaultPartyCreateEvent;
import net.pretronic.dkfriends.common.event.party.DefaultPartyDeleteEvent;
import net.pretronic.libraries.document.type.DocumentFileType;
import net.pretronic.libraries.utility.Iterators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

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
        DefaultParty party = new DefaultParty(dkFriends,UUID.randomUUID(),"Unset","Unset",false);

        PartyCreateEvent event = new DefaultPartyCreateEvent(dkFriends,party,ownerId);
        dkFriends.getEventBus().callEvent(PartyCreateEvent.class,event);
        if(event.isCancelled()) return null;

        dkFriends.getStorage().getParties().insert()
                .set("Id",party.getId())
                .set("Public",party.isPublic())
                .set("Category",party.getCategory())
                .set("Topic",party.getTopic())
                .set("Properties", DocumentFileType.JSON.getWriter().write(party.getProperties(),false))
                .set("Time",party.getCreationTime())
                .execute();
        this.parties.add(party);

        party.addInternal(new DefaultPartyMember(dkFriends,party.getId(),ownerId,party.getCreationTime(),PartyRole.LEADER));

        return party;
    }

    @Override
    public void deleteParty(UUID partyId) {
        deleteParty(getParty(partyId));
    }

    @Override
    public void deleteParty(Party party) {
        this.parties.remove(party);

        PartyDeleteEvent event = new DefaultPartyDeleteEvent(dkFriends,party);
        dkFriends.getEventBus().callEvent(PartyDeleteEvent.class,event);
        if(event.isCancelled()) return;

        dkFriends.getStorage().getParties().delete()
                .where("Id",party.getId())
                .execute();

        this.dkFriends.getStorage().getParties().delete().where("Id",party.getId()).execute();
        this.parties.remove(party);
    }

    private Collection<Party> getOrLoadParties(){
        if(this.parties == null){//@Todo maybe optimize
            this.parties = new ArrayList<>();

            DatabaseCollection parties = dkFriends.getStorage().getParties();
            DatabaseCollection members = dkFriends.getStorage().getPartiesMembers();

            for (QueryResultEntry entry : parties.find().execute()) {
                this.parties.add(new DefaultParty(dkFriends,entry.getUniqueId("Id")
                        ,entry.getString("Topic")
                        ,entry.getString("Category")
                        ,entry.getBoolean("Public")));
            }

            DefaultParty party = null;

            for (QueryResultEntry entry : members.find().orderBy("PartyId", SearchOrder.ASC).execute()) {
                UUID partyId = entry.getUniqueId("PartyId");
                if(party == null || !party.getId().equals(partyId)) party = (DefaultParty) Iterators.findOne(this.parties, party1 -> party1.getId().equals(partyId));
                party.addInternal(new DefaultPartyMember(dkFriends,partyId,entry.getUniqueId("PlayerId")
                        ,entry.getLong("Time")
                        ,PartyRole.valueOf(entry.getString("Role"))));
            }
        }
        return this.parties;
    }
}
