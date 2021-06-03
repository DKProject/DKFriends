package net.pretronic.dkfriends.api.event.clan.change;

import net.pretronic.libraries.event.network.NetworkEvent;

@NetworkEvent(ignoreNetworkException = true)
public interface ClanChangeTagEvent extends ClanChangeEvent {

    String getOldTag();

    String getNewTag();

    void setNewTag(String newTag);
}
