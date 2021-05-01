package net.pretronic.dkfriends.api.party;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;

import java.util.UUID;

public interface PartyInvitation {

    UUID getPartyId();

    Party getParty();

    UUID getPlayerId();

    DKFriendsPlayer getPlayer();

    UUID getInviterId();

    DKFriendsPlayer getInviter();

    long getInvitationTime();


    PartyMember accept();

    void deny();

}
