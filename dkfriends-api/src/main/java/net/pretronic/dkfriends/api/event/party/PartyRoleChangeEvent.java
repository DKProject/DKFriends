package net.pretronic.dkfriends.api.event.party;

import net.pretronic.dkfriends.api.event.DKFriendsPlayerEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.party.PartyRole;
import net.pretronic.libraries.event.network.NetworkEvent;

@NetworkEvent(ignoreNetworkException = true)
public interface PartyRoleChangeEvent extends DKFriendsPlayerEvent {

    Party getParty();

    PartyMember getMember();

    PartyRole getNewRole();

    PartyRole getOldRole();
}
