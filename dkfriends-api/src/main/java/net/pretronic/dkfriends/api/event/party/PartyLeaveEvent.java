package net.pretronic.dkfriends.api.event.party;

import net.pretronic.dkfriends.api.event.DKFriendsPlayerEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.libraries.event.Cancellable;
import net.pretronic.libraries.event.network.NetworkEvent;

import java.util.UUID;

@NetworkEvent(ignoreNetworkException = true)
public interface PartyLeaveEvent extends DKFriendsPlayerEvent, Cancellable {

    UUID getPartyId();

    Party getParty();

    PartyMember getMember();

    UUID getExecutorId();

    DKFriendsPlayer getExecutor();

    String getCause();
}
