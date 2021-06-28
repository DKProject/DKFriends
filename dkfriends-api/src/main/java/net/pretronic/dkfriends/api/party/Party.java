package net.pretronic.dkfriends.api.party;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.libraries.document.Document;

import java.util.Collection;
import java.util.UUID;

public interface Party {

    String DEFAULT_MESSAGE_CHANNEL = "MESSAGE";

    String DEFAULT_TELEPORT_TYPE = "SERVER";

    String DEFAULT_LEAVE_CAUSE = "LEAVE";

    String KICK_LEAVE_CAUSE = "KICK";

    UUID getId();


    boolean isPublic();

    void setPublic(boolean public0);


    String getCategory();

    void setCategory(String category);


    String getTopic();

    void setTopic(String topic);


    PartyMember getLeader();

    Collection<PartyMember> getMembers();

    int getSize();

    int getMaxSize();

    PartyMember getMember(UUID uniqueId);

    boolean isMember(UUID uniqueId);


    default PartyMember addMember(UUID uniqueId){
        return addMember(uniqueId,PartyRole.GUEST);
    }

    PartyMember addMember(UUID uniqueId,PartyRole role);


    default void removeMember(PartyMember member,String cause){
        removeMember(member,cause,null);
    }

    void removeMember(PartyMember member,String cause,DKFriendsPlayer executor);

    void removeMember(UUID uniqueId,String cause,DKFriendsPlayer executor);

    default void removeMember(UUID uniqueId,String cause){
        removeMember(uniqueId, cause,null);
    }

    default void leaveMember(PartyMember member){
        removeMember(member,DEFAULT_LEAVE_CAUSE);
    }

    default void leaveMember(UUID uniqueId){
        removeMember(uniqueId,DEFAULT_LEAVE_CAUSE);
    }

    default void kickMember(PartyMember member){
        removeMember(member,KICK_LEAVE_CAUSE);
    }

    default void kickMember(UUID uniqueId){
        removeMember(uniqueId,KICK_LEAVE_CAUSE);
    }

    Collection<PartyInvitation> getInvitations();

    PartyInvitation getInvitation(DKFriendsPlayer player);

    PartyInvitation getInvitation(UUID uniqueId);

    boolean hasInvitation(DKFriendsPlayer player);

    boolean hasInvitation(UUID uniqueId);


    PartyInvitation invite(DKFriendsPlayer inviter, UUID uniqueId);


    PartyMember acceptInvitation(UUID uniqueId);

    PartyMember acceptInvitation(DKFriendsPlayer player);

    PartyMember acceptInvitation(PartyInvitation invitation);


    void denyInvitation(UUID uniqueId);

    void denyInvitation(DKFriendsPlayer player);

    void denyInvitation(PartyInvitation invitation);


    boolean canInteract(UUID uniqueId,UUID target);

    boolean canInteract(DKFriendsPlayer player,DKFriendsPlayer target);


    Document getProperties();


    long getCreationTime();


    default void sendMessage(DKFriendsPlayer sender,String message){
        sendMessage(sender,DEFAULT_MESSAGE_CHANNEL,message);
    }

    void sendMessage(DKFriendsPlayer sender,String channel,String message);

    default void teleport(String target){
        teleport(DEFAULT_TELEPORT_TYPE,target);
    }

    void teleport(String type,String target);

    void delete();

}
