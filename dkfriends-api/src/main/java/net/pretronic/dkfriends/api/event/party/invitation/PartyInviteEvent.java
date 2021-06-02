package net.pretronic.dkfriends.api.event.party.invitation;

import net.pretronic.dkfriends.api.event.DKFriendsPlayerEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.libraries.event.Cancellable;
import net.pretronic.libraries.event.network.NetworkEvent;

import java.util.UUID;

@NetworkEvent(ignoreNetworkException = true)
public interface PartyInviteEvent extends DKFriendsPlayerEvent, Cancellable {

    UUID getPartyId();

    Party getParty();

    PartyInvitation getInvitation();
}
