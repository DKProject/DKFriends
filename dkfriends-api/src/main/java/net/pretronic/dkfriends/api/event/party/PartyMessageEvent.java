package net.pretronic.dkfriends.api.event.party;

import net.pretronic.dkfriends.api.event.DKFriendsEvent;
import net.pretronic.dkfriends.api.event.DKFriendsPlayerEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.libraries.event.Cancellable;

import java.util.UUID;

public interface PartyMessageEvent extends DKFriendsEvent, Cancellable {

    Party getParty();

    UUID getSenderId();

    DKFriendsPlayer getSender();

    String getChannel();

    void setChannel(String channel);

    String getMessage();

    void setMessage(String message);
}
