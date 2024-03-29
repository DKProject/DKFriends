package net.pretronic.dkfriends.api.event.party.invitation;

import net.pretronic.dkfriends.api.event.DKFriendsPlayerEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.libraries.event.network.NetworkEvent;

@NetworkEvent(ignoreNetworkException = true)
public interface PartyInvitationDenyEvent extends DKFriendsPlayerEvent {

    Party getParty();

    PartyInvitation getInvitation();
}
