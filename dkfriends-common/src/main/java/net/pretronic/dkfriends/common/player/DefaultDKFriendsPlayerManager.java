package net.pretronic.dkfriends.common.player;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.DKFriendsPlayerManager;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.libraries.caching.Cache;
import net.pretronic.libraries.caching.CacheQuery;
import net.pretronic.libraries.caching.synchronisation.ShadowArraySynchronizableCache;
import net.pretronic.libraries.caching.synchronisation.SynchronizableCache;
import net.pretronic.libraries.utility.Iterators;
import net.pretronic.libraries.utility.Validate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class DefaultDKFriendsPlayerManager implements DKFriendsPlayerManager {

    private final DefaultDKFriends dkFriends;
    private final SynchronizableCache<DefaultDKFriendsPlayer,UUID> players;

    public DefaultDKFriendsPlayerManager(DefaultDKFriends dkFriends) {
        this.dkFriends = dkFriends;
        this.players = new ShadowArraySynchronizableCache<>();
        this.players.setMaxSize(1024);
        this.players.setExpireAfterAccess(10, TimeUnit.MINUTES);
        this.players.registerQuery("get", new PlayerGetter());
        this.players.setClearOnDisconnect(true);
        this.players.setSkipOnDisconnect(true);
    }

    public SynchronizableCache<DefaultDKFriendsPlayer, UUID> getPlayerCache() {
        return players;
    }

    @Override
    public DKFriendsPlayer getPlayer(UUID uniqueId) {
        return this.players.get("get", uniqueId);
    }

    @Override
    public DKFriendsPlayer getLoadedPlayer(UUID uniqueId) {
        return Iterators.findOne(players.getCachedObjects(), friend -> friend.getId().equals(uniqueId));
    }

    private class PlayerGetter implements CacheQuery<DefaultDKFriendsPlayer> {

        @Override
        public boolean check(DefaultDKFriendsPlayer player, Object[] identifiers) {
            return player.getId().equals(identifiers[0]);
        }

        @Override
        public void validate(Object[] identifiers) {
            Validate.isTrue(identifiers.length == 1 && (identifiers[0] instanceof UUID || identifiers[0] instanceof String));
        }

        @Override
        public DefaultDKFriendsPlayer load(Object[] identifiers) {
            return new DefaultDKFriendsPlayer(dkFriends, (UUID) identifiers[0]);
        }
    }
}
