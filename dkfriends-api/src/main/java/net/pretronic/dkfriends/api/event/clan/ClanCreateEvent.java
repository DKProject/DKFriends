package net.pretronic.dkfriends.api.event.clan;

import net.pretronic.libraries.event.Cancellable;
import net.pretronic.libraries.event.network.NetworkEvent;

@NetworkEvent(ignoreNetworkException = true)
public interface ClanCreateEvent extends ClanEvent, Cancellable {

    String getName();

    void setName(String name);

    String getTag();

    void setTag(String tag);

}
