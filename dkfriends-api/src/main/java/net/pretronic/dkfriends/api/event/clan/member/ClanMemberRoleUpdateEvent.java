package net.pretronic.dkfriends.api.event.clan.member;

import net.pretronic.dkfriends.api.clan.ClanRole;
import net.pretronic.libraries.event.Cancellable;
import net.pretronic.libraries.event.network.NetworkEvent;

@NetworkEvent(ignoreNetworkException = true)
public interface ClanMemberRoleUpdateEvent extends ClanMemberEvent, Cancellable {

    String getCause();

    ClanRole getNewRole();
}
