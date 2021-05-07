package net.pretronic.dkfriends.common.party;

import net.pretronic.dkfriends.api.event.party.*;
import net.pretronic.dkfriends.api.event.party.invitation.PartyInvitationAcceptEvent;
import net.pretronic.dkfriends.api.event.party.invitation.PartyInvitationDenyEvent;
import net.pretronic.dkfriends.api.event.party.invitation.PartyInviteEvent;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.party.PartyRole;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.common.event.party.DefaultPartyJoinEvent;
import net.pretronic.dkfriends.common.event.party.DefaultPartyLeaveEvent;
import net.pretronic.dkfriends.common.event.party.invitation.DefaultPartyInvitationAcceptEvent;
import net.pretronic.dkfriends.common.event.party.invitation.DefaultPartyInvitationDenyEvent;
import net.pretronic.dkfriends.common.event.party.invitation.DefaultPartyInviteEvent;
import net.pretronic.dkfriends.common.event.party.DefaultPartyMessageEvent;
import net.pretronic.dkfriends.common.event.party.DefaultPartyTeleportEvent;
import net.pretronic.libraries.document.Document;
import net.pretronic.libraries.utility.Iterators;
import net.pretronic.libraries.utility.annonations.Internal;
import net.pretronic.libraries.utility.exception.OperationFailedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.function.Predicate;

public class DefaultParty implements Party {

    private final DefaultDKFriends dkfriends;
    private final UUID id;
    private final long creationTime;
    private final Document properties;

    private final Collection<PartyMember> members;
    private final Collection<PartyInvitation> invitations;

    private String topic;
    private String category;
    private boolean public0;

    public DefaultParty(DefaultDKFriends dkfriends,UUID id,UUID owner) {
        this.dkfriends = dkfriends;
        this.id = id;
        this.creationTime = System.currentTimeMillis();
        this.members = new ArrayList<>();
        this.invitations = new ArrayList<>();
        this.properties = Document.newDocument();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isPublic() {
        return public0;
    }

    @Override
    public void setPublic(boolean public0) {
        dkfriends.getStorage().getParties().update().set("Public",public0).where("Id",id).execute();
        this.public0 = public0;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String category) {
        dkfriends.getStorage().getParties().update().set("Category",category).where("Id",id).execute();
        this.category = category;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public void setTopic(String topic) {
        dkfriends.getStorage().getParties().update().set("Topic",topic).where("Id",id).execute();
        this.topic = topic;
    }

    @Override
    public PartyMember getLeader() {
        return Iterators.findOne(this.members, member -> member.getRole().equals(PartyRole.LEADER));
    }

    @Override
    public Collection<PartyMember> getMembers() {
        return Collections.unmodifiableCollection(this.members);
    }

    @Override
    public PartyMember getMember(UUID uniqueId) {
        return Iterators.findOne(this.members, member -> member.getPlayerId().equals(uniqueId));
    }

    @Override
    public boolean isMember(UUID uniqueId) {
        return getMember(uniqueId) != null;
    }

    @Override
    public PartyMember addMember(UUID uniqueId, PartyRole role) {
        if(isMember(uniqueId)) throw new IllegalArgumentException("Already a member of this party");

        PartyMember member = new DefaultPartyMember(dkfriends,this.id,uniqueId,System.currentTimeMillis(),PartyRole.GUEST);

        PartyJoinEvent event = new DefaultPartyJoinEvent(member);
        dkfriends.getEventBus().callEvent(PartyJoinEvent.class,event);
        if(event.isCancelled()) return null;

        addInternal(member);

        return member;
    }

    @Internal
    public void addInternal(PartyMember member){
        dkfriends.getStorage().getPartiesMembers().insert()
                .set("PartyId",id)
                .set("PlayerId",member.getPlayerId())
                .set("Role",member.getRole())
                .set("Time",member.getJoinTime())
                .execute();
        this.members.add(member);
    }

    @Override
    public void removeMember(PartyMember member, String cause) {
        if(!member.getPartyId().equals(id)) throw new IllegalArgumentException("Member belongs not to this party");

        PartyLeaveEvent event = new DefaultPartyLeaveEvent(member,cause);
        dkfriends.getEventBus().callEvent(PartyLeaveEvent.class,event);
        if(event.isCancelled()) return;

        dkfriends.getStorage().getPartiesMembers().delete().where("PlayerId",member.getPlayerId()).execute();
        this.members.remove(member);

        if(this.members.size() <= 1){
            delete();
        }else if(member.getRole() == PartyRole.LEADER){
            PartyMember newLeader = Iterators.findOne(this.members, member1 -> member1.getRole() == PartyRole.MODERATOR);
            if(newLeader == null) newLeader = this.members.iterator().next();
            newLeader.setRole(PartyRole.LEADER);
        }

    }

    @Override
    public void removeMember(UUID uniqueId, String cause) {
        removeMember(getMember(uniqueId),cause);
    }

    @Override
    public Collection<PartyInvitation> getInvitations() {
        return invitations;
    }

    @Override
    public PartyInvitation getInvitation(DKFriendsPlayer player) {
        return getInvitation(player.getId());
    }

    @Override
    public PartyInvitation getInvitation(UUID uniqueId) {
        return Iterators.findOne(invitations, invitation -> invitation.getPlayerId().equals(uniqueId));
    }

    @Override
    public boolean hasInvitation(DKFriendsPlayer player) {
        return getInvitation(player) != null;
    }

    @Override
    public boolean hasInvitation(UUID uniqueId) {
        return getInvitation(uniqueId) != null;
    }

    @Override
    public boolean canInteract(UUID uniqueId, UUID targetId) {
        PartyMember member = getMember(uniqueId);
        if(member == null || member.getRole() == PartyRole.GUEST) return false;
        PartyMember target = getMember(targetId);

        if(target == null || member.getRole() == PartyRole.GUEST) return true;
        else return member.getRole() == PartyRole.LEADER;
    }

    @Override
    public boolean canInteract(DKFriendsPlayer player, DKFriendsPlayer target) {
        return canInteract(player.getId(),target.getId());
    }

    @Override
    public PartyInvitation invite(DKFriendsPlayer inviter, UUID uniqueId) {
        if(hasInvitation(uniqueId)) throw new IllegalArgumentException("Player has already an invitation to this party");
        if(isMember(uniqueId)) throw new IllegalArgumentException("Player is already an member of this party");

        PartyInvitation invitation = new DefaultPartyInvitation(dkfriends,this.id,uniqueId,inviter.getId(),System.currentTimeMillis());
        PartyInviteEvent event = new DefaultPartyInviteEvent(dkfriends,uniqueId,invitation);
        dkfriends.getEventBus().callEvent(PartyInviteEvent.class,event);
        if(event.isCancelled()) return null;

        dkfriends.getStorage().getPartiesInvitations().insert()
                .set("PartyId",this.id)
                .set("PlayerId",uniqueId)
                .set("InviterId",inviter.getId())
                .set("Time",invitation.getInvitationTime())
                .execute();

        this.invitations.add(invitation);

        return invitation;
    }

    @Override
    public PartyMember acceptInvitation(UUID uniqueId) {
        return acceptInvitation(getInvitation(uniqueId));
    }

    @Override
    public PartyMember acceptInvitation(DKFriendsPlayer player) {
        return acceptInvitation(player.getId());
    }

    @Override
    public PartyMember acceptInvitation(PartyInvitation invitation) {
        if(!invitation.getPartyId().equals(this.id)) throw new IllegalArgumentException("Invitation does not belong to this party");

        PartyInvitationAcceptEvent event = new DefaultPartyInvitationAcceptEvent(invitation);
        dkfriends.getEventBus().callEvent(PartyInvitationAcceptEvent.class,event);

        deleteInvitation(invitation);

        return addMember(invitation.getPlayerId());
    }

    @Override
    public void denyInvitation(UUID uniqueId) {
        denyInvitation(getInvitation(uniqueId));
    }

    @Override
    public void denyInvitation(DKFriendsPlayer player) {
        denyInvitation(player.getId());
    }

    @Override
    public void denyInvitation(PartyInvitation invitation) {
        if(!invitation.getPartyId().equals(this.id)) throw new IllegalArgumentException("Invitation does not belong to this party");

        PartyInvitationDenyEvent event = new DefaultPartyInvitationDenyEvent(invitation);
        dkfriends.getEventBus().callEvent(PartyInvitationDenyEvent.class,event);

        deleteInvitation(invitation);
    }

    private void deleteInvitation(PartyInvitation invitation){
        dkfriends.getStorage().getPartiesInvitations().delete()
                .where("PartyId",invitation.getPartyId())
                .where("PlayerId",invitation.getPlayerId())
                .execute();
        this.invitations.remove(invitation);
    }

    @Override
    public Document getProperties() {
        return properties;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public void sendMessage(DKFriendsPlayer sender,String channel, String message) {
        PartyMessageEvent event = new DefaultPartyMessageEvent(dkfriends,this,sender.getId(),channel,message);
        dkfriends.getEventBus().callEvent(event);
    }

    @Override
    public void teleport(String type, String target) {
        PartyTeleportEvent event = new DefaultPartyTeleportEvent(this,type,target);
        dkfriends.getEventBus().callEvent(PartyTeleportEvent.class,event);
    }

    @Override
    public void delete() {
        dkfriends.getPartyManager().deleteParty(this);
    }
}
