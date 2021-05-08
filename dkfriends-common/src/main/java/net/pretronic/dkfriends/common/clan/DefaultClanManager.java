package net.pretronic.dkfriends.common.clan;

import net.pretronic.databasequery.api.query.result.QueryResultEntry;
import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanManager;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.libraries.caching.ArrayCache;
import net.pretronic.libraries.caching.Cache;
import net.pretronic.libraries.caching.CacheQuery;
import net.pretronic.libraries.document.Document;
import net.pretronic.libraries.document.type.DocumentFileType;
import net.pretronic.libraries.utility.Validate;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class DefaultClanManager implements ClanManager {

    private final DefaultDKFriends dkFriends;

    private final Cache<Clan> clanCache;

    public DefaultClanManager(DefaultDKFriends dkFriends) {
        this.dkFriends = dkFriends;
        this.clanCache = new ArrayCache<>();
        this.clanCache.setExpireAfterAccess(30, TimeUnit.MINUTES)
                .setMaxSize(1000)
                .registerQuery("byId", new ByIdCacheQuery())
                .registerQuery("byName", new ByNameCacheQuery())
                .registerQuery("byTag", new ByTagCacheQuery())
                .registerQuery("byPlayerId", new ByPlayerIdCacheQuery());
    }

    @Override
    public Collection<Clan> getClans() {
        return null;
    }

    @Override
    public Clan getClan(UUID id) {
        Validate.notNull(id);
        return this.clanCache.get("byId", id);
    }

    @Override
    public Clan getClan(String name) {
        return this.clanCache.get("byName", name);
    }

    @Override
    public Clan getClanByTag(String tag) {
        return this.clanCache.get("byTag", tag);
    }

    @Override
    public Clan getClanByPlayer(UUID playerId) {
        return this.clanCache.get("byPlayerId", playerId);
    }

    @Override
    public Clan createClan(String name, String tag) {
        Validate.notNull((Object) name, (Object) tag);
        UUID clanId = UUID.randomUUID();

        if(existClan(name, tag)) return null;

        this.dkFriends.getStorage().getClans().insert()
                .set("Id", clanId)
                .set("Name", name)
                .set("Tag", tag)
                .set("Status", (Object) null)
                .set("Properties", "{}")
                .execute();

        return new DefaultClan(this.dkFriends, clanId, name, tag, null, Document.newDocument());
    }

    @Override
    public boolean existClan(String name, String tag) {
        return this.dkFriends.getStorage().getClans().find()
                .or(or -> or.where("Name", name).where("Tag", tag))
                .execute()
                .firstOrNull() != null;
    }

    @Override
    public void deleteClan(UUID clanId) {
        Validate.notNull(clanId);

        this.dkFriends.getStorage().getClans().delete()
                .where("Id", clanId)
                .execute();

        this.clanCache.remove("byId", clanId);
    }

    @Override
    public boolean deleteClan(Clan clan) {
        Validate.notNull(clan);
        deleteClan(clan.getId());
        return true;
    }

    private Clan getClanByResultEntry(QueryResultEntry resultEntry) {
        if(resultEntry == null) return null;
        return new DefaultClan(this.dkFriends,
                resultEntry.getUniqueId("Id"),
                resultEntry.getString("Name"),
                resultEntry.getString("Tag"),
                resultEntry.getString("Status"),
                DocumentFileType.JSON.getReader().read(resultEntry.getString("Properties")));
    }

    private class ByIdCacheQuery implements CacheQuery<Clan> {

        @Override
        public boolean check(Clan clan, Object[] identifiers) {
            UUID id = (UUID) identifiers[0];
            return clan.getId().equals(id);
        }

        @Override
        public void validate(Object[] identifiers) {
            Validate.isTrue(identifiers.length == 1, "Error: Clan cache ById wrong identifier length");
            Validate.isTrue(identifiers[0] instanceof UUID, "Error: Clan cache ById wrong identifier type");
        }

        @Override
        public Clan load(Object[] identifiers) {
            UUID id = (UUID) identifiers[0];
            return getClanByResultEntry(dkFriends.getStorage().getClans().find().where("Id", id).execute().firstOrNull());
        }
    }

    private class ByNameCacheQuery implements CacheQuery<Clan> {

        @Override
        public boolean check(Clan clan, Object[] identifiers) {
            String name = (String) identifiers[0];
            return clan.getName().equalsIgnoreCase(name);
        }

        @Override
        public void validate(Object[] identifiers) {
            Validate.isTrue(identifiers.length == 1, "Error: Clan cache ByName wrong identifier length");
            Validate.isTrue(identifiers[0] instanceof String, "Error: Clan cache ByName wrong identifier type");
        }

        @Override
        public Clan load(Object[] identifiers) {
            String name = (String) identifiers[0];
            return getClanByResultEntry(dkFriends.getStorage().getClans().find().where("Name", name).execute().firstOrNull());
        }
    }

    private class ByTagCacheQuery implements CacheQuery<Clan> {

        @Override
        public boolean check(Clan clan, Object[] identifiers) {
            String tag = (String) identifiers[0];
            return clan.getTag().equalsIgnoreCase(tag);
        }

        @Override
        public void validate(Object[] identifiers) {
            Validate.isTrue(identifiers.length == 1, "Error: Clan cache ByTag wrong identifier length");
            Validate.isTrue(identifiers[0] instanceof String, "Error: Clan cache ByTag wrong identifier type");
        }

        @Override
        public Clan load(Object[] identifiers) {
            String tag = (String) identifiers[0];
            return getClanByResultEntry(dkFriends.getStorage().getClans().find().where("Tag", tag).execute().firstOrNull());
        }
    }

    private class ByPlayerIdCacheQuery implements CacheQuery<Clan> {

        @Override
        public boolean check(Clan clan, Object[] identifiers) {
            UUID playerId = (UUID) identifiers[0];
            return clan.isMember(playerId);
        }

        @Override
        public void validate(Object[] identifiers) {
            Validate.isTrue(identifiers.length == 1, "Error: Clan cache ByPlayerId wrong identifier length");
            Validate.isTrue(identifiers[0] instanceof UUID, "Error: Clan cache ByPlayerId wrong identifier type");
        }

        @Override
        public Clan load(Object[] identifiers) {
            UUID playerId = (UUID) identifiers[0];
            return getClanByResultEntry(dkFriends.getStorage().getClans().find()
                    .get(dkFriends.getStorage().getClans(), "*")
                    .join(dkFriends.getStorage().getClanMembers()).on("Id","ClanId")
                    .where("PlayerId", playerId)
                    .execute().firstOrNull());
        }
    }
}
