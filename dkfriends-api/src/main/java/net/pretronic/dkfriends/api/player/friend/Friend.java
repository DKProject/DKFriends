package net.pretronic.dkfriends.api.player.friend;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public interface Friend {

    UUID getPlayerId();

    DKFriendsPlayer getPlayer();


    UUID getFriendId();

    DKFriendsPlayer getFriend();


    boolean isFavorite();

    void setFavorite(boolean favorite);


    String getRelation();

    void setRelation(String relation);


    long getFriendSince();


    void remove();

}
