package net.pretronic.dkfriends.api.clan;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.libraries.document.Document;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface Clan {

    String DEFAULT_MESSAGE_CHANNEL = "MESSAGE";


    UUID getId();

    String getName();

    boolean setName(String name);


    String getTag();

    boolean setTag(String tag);


    String getStatus();

    boolean setStatus(String status);


    Document getProperties();


    List<ClanMember> getSortedMembers();

    Collection<ClanMember> getOnlineMembers();

    Collection<ClanMember> getMembers();

    ClanMember getMember(UUID playerId);

    boolean isMember(UUID playerId);

    int getSize();


    ClanMember addMember(UUID playerId);

    ClanMember addMember(UUID playerId, ClanRole role);

    boolean removeMember(ClanMember member, String cause);


    ClanMember acceptInvitation(ClanInvitation invitation);

    void denyInvitation(ClanInvitation invitation);


    Collection<ClanInvitation> getInvitations();

    ClanInvitation getInvitation(UUID playerId);


    boolean hasInvitation(UUID playerId);

    ClanInvitation sendInvitation(DKFriendsPlayer inviter, UUID playerId);

    default void sendMessage(DKFriendsPlayer sender, String message){
        sendMessage(sender,message,DEFAULT_MESSAGE_CHANNEL);
    }

    void sendMessage(DKFriendsPlayer sender, String message, String channel);
}
