package net.pretronic.dkfriends.api.event.party;

import net.pretronic.dkfriends.api.event.DKFriendsPlayerEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.libraries.event.Cancellable;

public interface PartyJoinEvent extends DKFriendsPlayerEvent, Cancellable {

    Party getParty();

    PartyMember getMember();
}
