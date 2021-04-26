package net.pretronic.dkfriends.common.party;

import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyInvitation;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.party.PartyRole;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.libraries.document.Document;

import java.util.Collection;
import java.util.UUID;

public class DefaultParty implements Party {

    private final UUID id;

    public DefaultParty(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isPublic() {
        return false;
    }

    @Override
    public void setPublic(boolean public0) {

    }

    @Override
    public int getMaximumSize() {
        return 0;
    }

    @Override
    public void setMaximumSize(int size) {

    }

    @Override
    public String getCategory() {
        return null;
    }

    @Override
    public void setCategory(String category) {

    }

    @Override
    public String getTopic() {
        return null;
    }

    @Override
    public void setTopic(String topic) {

    }

    @Override
    public PartyMember getLeader() {
        return null;
    }

    @Override
    public Collection<PartyMember> getMembers() {
        return null;
    }

    @Override
    public PartyMember getMember(UUID uniqueId) {
        return null;
    }

    @Override
    public boolean isMember(UUID uniqueId) {
        return getMember(uniqueId) != null;
    }

    @Override
    public PartyMember addMember(UUID uniqueId, PartyRole role) {
        return null;
    }

    @Override
    public void removeMember(PartyMember member) {

    }

    @Override
    public void removeMember(UUID uniqueId) {

    }

    @Override
    public void leaveMember(UUID uniqueId) {

    }

    @Override
    public PartyInvitation invite(DKFriendsPlayer inviter, UUID uniqueId) {
        return null;
    }

    @Override
    public Document getProperties() {
        return null;
    }

    @Override
    public long getCreationTime() {
        return 0;
    }

    @Override
    public void delete() {

    }
}
