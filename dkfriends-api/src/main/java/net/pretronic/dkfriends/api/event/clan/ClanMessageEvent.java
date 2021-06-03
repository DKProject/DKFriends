package net.pretronic.dkfriends.api.event.clan;

import net.pretronic.libraries.event.network.NetworkEvent;

@NetworkEvent(ignoreNetworkException = true)
public interface ClanMessageEvent extends ClanEvent {

    String getChannel();

    void setChannel(String channel);

    String getMessage();

    void setMessage(String message);
}
