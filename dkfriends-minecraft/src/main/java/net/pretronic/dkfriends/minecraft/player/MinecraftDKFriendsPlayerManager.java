package net.pretronic.dkfriends.minecraft.player;

import net.pretronic.dkfriends.api.DKFriends;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.DKFriendsPlayerManager;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.libraries.caching.CacheQuery;
import net.pretronic.libraries.caching.synchronisation.ShadowArraySynchronizableCache;
import net.pretronic.libraries.caching.synchronisation.SynchronizableCache;
import net.pretronic.libraries.utility.Iterators;
import net.pretronic.libraries.utility.Validate;
import net.pretronic.libraries.utility.interfaces.Initializable;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.MinecraftPlayer;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MinecraftDKFriendsPlayerManager implements DKFriendsPlayerManager, Initializable<DefaultDKFriends> {

    private DefaultDKFriends dkFriends;
    private final SynchronizableCache<MinecraftDKFriendsPlayer,UUID> players;

    public MinecraftDKFriendsPlayerManager() {
        this.players = new ShadowArraySynchronizableCache<>();
        this.players.setMaxSize(1024);
        this.players.setExpireAfterAccess(8, TimeUnit.MINUTES);
        this.players.registerQuery("get", new PlayerGetter());
        this.players.setClearOnDisconnect(true);
        this.players.setSkipOnDisconnect(true);
    }

    public SynchronizableCache<MinecraftDKFriendsPlayer, UUID> getPlayerCache() {
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

    @Override
    public void initialise(DefaultDKFriends dkFriends) {
        this.dkFriends = dkFriends;
    }

    private class PlayerGetter implements CacheQuery<MinecraftDKFriendsPlayer> {

        @Override
        public boolean check(MinecraftDKFriendsPlayer player, Object[] identifiers) {
            return player.getId().equals(identifiers[0]);
        }

        @Override
        public void validate(Object[] identifiers) {
            Validate.isTrue(identifiers.length == 1 && (identifiers[0] instanceof UUID || identifiers[0] instanceof String));
        }

        @Override
        public MinecraftDKFriendsPlayer load(Object[] identifiers) {
            MinecraftPlayer player = McNative.getInstance().getPlayerManager().getPlayer((UUID) identifiers[0]);
            if(player == null) return null;
            return new MinecraftDKFriendsPlayer(dkFriends, (UUID) identifiers[0]);//player
        }
    }
}
