package net.pretronic.dkfriends.api.event.clan.change;

import net.pretronic.libraries.event.network.NetworkEvent;

@NetworkEvent(ignoreNetworkException = true)
public interface ClanChangeStatusEvent extends ClanChangeEvent {

    String getOldStatus();

    String getNewStatus();

    void setNewStatus(String newStatus);
}
