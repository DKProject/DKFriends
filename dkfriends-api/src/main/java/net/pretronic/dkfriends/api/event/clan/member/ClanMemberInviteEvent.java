package net.pretronic.dkfriends.api.event.clan.member;

import net.pretronic.dkfriends.api.clan.ClanInvitation;
import net.pretronic.dkfriends.api.event.clan.ClanEvent;
import net.pretronic.libraries.event.Cancellable;
import net.pretronic.libraries.event.network.NetworkEvent;

@NetworkEvent(ignoreNetworkException = true)
public interface ClanMemberInviteEvent extends ClanEvent, Cancellable {

    ClanInvitation getInvitation();

}
