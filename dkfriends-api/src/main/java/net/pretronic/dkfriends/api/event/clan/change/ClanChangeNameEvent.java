package net.pretronic.dkfriends.api.event.clan.change;

import net.pretronic.libraries.event.network.NetworkEvent;

@NetworkEvent(ignoreNetworkException = true)
public interface ClanChangeNameEvent extends ClanChangeEvent {

    String getOldName();

    String getNewName();

    void setNewName(String newName);
}
