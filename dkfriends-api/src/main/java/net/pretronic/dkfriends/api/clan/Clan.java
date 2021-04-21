package net.pretronic.dkfriends.api.clan;

import net.pretronic.libraries.document.Document;

import java.util.Collection;
import java.util.UUID;

public interface Clan {

    UUID getId();

    String getName();

    void setName(String name);


    String getTag();

    void setTag(String tag);


    String getStatus();

    void setStatus(String status);


    Document getProperties();


    Collection<ClanMember> getMembers();

    ClanMember getMember(UUID playerId);

    ClanMember addMember(UUID playerId);

    void removeMember(UUID playerId);


    Collection<ClanInvitation> getInvitations();

    ClanInvitation sendInvitation(UUID playerId);


}
