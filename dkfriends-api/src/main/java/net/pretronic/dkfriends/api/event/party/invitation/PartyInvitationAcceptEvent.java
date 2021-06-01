package net.pretronic.dkfriends.api.event.party.invitation;

import net.pretronic.dkfriends.api.event.DKFriendsPlayerEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.libraries.event.Cancellable;

import java.util.UUID;

public interface PartyInvitationAcceptEvent extends DKFriendsPlayerEvent {

    Party getParty();

    PartyInvitation getInvitation();
}
