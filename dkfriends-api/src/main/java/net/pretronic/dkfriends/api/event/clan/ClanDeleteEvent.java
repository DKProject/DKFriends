package net.pretronic.dkfriends.api.event.clan;

import net.pretronic.libraries.event.Cancellable;
import net.pretronic.libraries.event.network.NetworkEvent;

@NetworkEvent(ignoreNetworkException = true)
public interface ClanDeleteEvent extends ClanEvent, Cancellable {

}
