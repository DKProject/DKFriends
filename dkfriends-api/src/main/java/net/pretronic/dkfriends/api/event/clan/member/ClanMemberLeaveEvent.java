package net.pretronic.dkfriends.api.event.clan.member;

import net.pretronic.libraries.event.Cancellable;
import net.pretronic.libraries.event.network.NetworkEvent;

@NetworkEvent(ignoreNetworkException = true)
public interface ClanMemberLeaveEvent extends ClanMemberEvent, Cancellable {

    String getCause();

}
