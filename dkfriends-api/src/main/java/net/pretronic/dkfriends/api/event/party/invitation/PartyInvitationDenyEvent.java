package net.pretronic.dkfriends.api.event.party.invitation;

import net.pretronic.dkfriends.api.event.DKFriendsPlayerEvent;
import net.pretronic.dkfriends.api.party.PartyInvitation;

public interface PartyInvitationDenyEvent extends DKFriendsPlayerEvent {

    PartyInvitation getInvitation();
}
