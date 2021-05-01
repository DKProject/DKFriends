package net.pretronic.dkfriends.api.event.party;

import net.pretronic.dkfriends.api.event.DKFriendsPlayerEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.libraries.event.Cancellable;

public interface PartyLeaveEvent extends DKFriendsPlayerEvent, Cancellable {

    String getCause();
}
