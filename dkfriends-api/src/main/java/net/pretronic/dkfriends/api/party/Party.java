package net.pretronic.dkfriends.api.party;

import net.pretronic.libraries.document.Document;

import java.util.Collection;
import java.util.UUID;

public interface Party {

    UUID getId();


    boolean isPublic();

    void setPublic(boolean public0);


    int getMaximumSize();

    void setMaximumSize(int size);


    String getCategory();

    void setCategory(String category);


    String getTopic();

    void setTopic(String topic);


    PartyMember getLeader();

    Collection<PartyMember> getMembers();

    PartyMember getMember(UUID uniqueId);

    boolean isMember(UUID uniqueId);


    default PartyMember addMember(UUID uniqueId){
        return addMember(uniqueId,PartyRole.GUEST);
    }

    PartyMember addMember(UUID uniqueId,PartyRole role);


    void removeMember(PartyMember member);

    void removeMember(UUID uniqueId);

    void leaveMember(UUID uniqueId);



    Document getProperties();


    long getCreationTime();


    void delete();

}
