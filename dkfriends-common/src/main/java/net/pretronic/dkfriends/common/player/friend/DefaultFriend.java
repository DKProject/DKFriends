package net.pretronic.dkfriends.common.player.friend;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.Friend;

import java.util.UUID;

public class DefaultFriend implements Friend {

    private final DKFriendsPlayer player;
    public final UUID friendId;
    private final long friendSince;

    private boolean favorite;
    private String relation;

    public DefaultFriend(DKFriendsPlayer player, UUID friendId, long friendSince, boolean favorite, String relation) {
        this.player = player;
        this.friendId = friendId;
        this.friendSince = friendSince;
        this.favorite = favorite;
        this.relation = relation;
    }

    @Override
    public UUID getPlayerId() {
        return player.getId();
    }

    @Override
    public DKFriendsPlayer getPlayer() {
        return player;
    }

    @Override
    public UUID getFriendId() {
        return friendId;
    }

    @Override
    public boolean isFavorite() {
        return favorite;
    }

    @Override
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String getRelation() {
        return relation;
    }

    @Override
    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Override
    public long getFriendSince() {
        return friendSince;
    }

    @Override
    public void remove() {
        player.removeFriend(this);
    }
}
