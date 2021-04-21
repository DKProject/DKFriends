package net.pretronic.dkfriends.player.friend;

import java.util.UUID;

public interface Friend {

    UUID getPlayerId();

    UUID getFriendId();


    boolean isFavorite();

    void setFavorite(boolean favorite);


    String getRelation();

    void setRelation(String relation);


    long getFriendSince();


    void remove();

}
