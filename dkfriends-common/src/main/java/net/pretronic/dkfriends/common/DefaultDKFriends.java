package net.pretronic.dkfriends.common;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.clan.ClanManager;
import net.pretronic.dkfriends.api.party.PartyManager;
import net.pretronic.dkfriends.api.player.DKFriendsPlayerManager;
import net.pretronic.dkfriends.common.party.DefaultPartyManager;
import net.pretronic.dkfriends.common.player.DefaultDKFriendsPlayerManager;
import net.pretronic.libraries.event.EventBus;

public class DefaultDKFriends implements DKFriends {

    private final String version;
    private final DKFriendsPlayerManager playerManager;
    private final PartyManager partyManager;
    private final ClanManager clanManager;
    private final DKFriendStorage storage;
    private final EventBus eventBus;

    public DefaultDKFriends(String version, DKFriendStorage storage, EventBus eventBus) {
        this.version = version;
        this.playerManager = new DefaultDKFriendsPlayerManager(this);
        this.partyManager = new DefaultPartyManager(this);
        this.clanManager = null;
        this.storage = storage;
        this.eventBus = eventBus;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public DKFriendsPlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public PartyManager getPartyManager() {
        return partyManager;
    }

    @Override
    public ClanManager getClanManager() {
        return clanManager;
    }

    public DKFriendStorage getStorage(){
        return storage;
    }

    public EventBus getEventBus(){
        return eventBus;
    }
}
