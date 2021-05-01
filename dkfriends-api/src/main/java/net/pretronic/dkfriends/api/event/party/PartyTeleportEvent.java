package net.pretronic.dkfriends.api.event.party;

import net.pretronic.dkfriends.api.event.DKFriendsEvent;
import net.pretronic.dkfriends.api.event.DKFriendsPlayerEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.libraries.event.Cancellable;

public interface PartyTeleportEvent extends DKFriendsEvent, Cancellable {

    Party getParty();

    String getType();

    String getTarget();
}
