package net.pretronic.dkfriends.common;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.clan.ClanManager;
import net.pretronic.dkfriends.api.party.PartyManager;
import net.pretronic.dkfriends.api.player.DKFriendsPlayerManager;
import net.pretronic.dkfriends.common.clan.DefaultClanManager;
import net.pretronic.dkfriends.common.party.DefaultPartyManager;
import net.pretronic.libraries.event.EventBus;
import net.pretronic.libraries.utility.interfaces.Initializable;

public class DefaultDKFriends implements DKFriends {

    private final String version;
    private final DKFriendsPlayerManager playerManager;
    private final PartyManager partyManager;
    private final ClanManager clanManager;
    private final DKFriendStorage storage;
    private final EventBus eventBus;

    @SuppressWarnings("unchecked")
    public DefaultDKFriends(String version, DKFriendStorage storage,DKFriendsPlayerManager playerManager, EventBus eventBus) {
        this.version = version;
        this.playerManager = playerManager;
        this.partyManager = new DefaultPartyManager(this);
        this.clanManager = new DefaultClanManager(this);
        this.storage = storage;
        this.eventBus = eventBus;

        if(playerManager instanceof Initializable) ((Initializable) playerManager).initialise(this);
        if(storage instanceof Initializable) ((Initializable) storage).initialise(this);
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
