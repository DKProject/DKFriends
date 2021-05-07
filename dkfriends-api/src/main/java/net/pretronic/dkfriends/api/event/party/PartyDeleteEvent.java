package net.pretronic.dkfriends.api.event.party;

import net.pretronic.dkfriends.api.event.DKFriendsEvent;
import net.pretronic.dkfriends.api.event.DKFriendsPlayerEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.libraries.event.Cancellable;

import java.util.UUID;

public interface PartyDeleteEvent extends DKFriendsEvent, Cancellable {

    UUID getPartyId();

    Party getParty();
}
