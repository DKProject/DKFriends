package net.pretronic.dkfriends.common;

import net.pretronic.databasequery.api.Database;
import net.pretronic.databasequery.api.collection.DatabaseCollection;
import net.pretronic.databasequery.api.collection.field.FieldOption;
import net.pretronic.databasequery.api.datatype.DataType;
import net.pretronic.databasequery.api.query.ForeignKey;

public class DKFriendStorage {

    private final Database database;

    private DatabaseCollection friends;
    private DatabaseCollection friendRequests;

    private DatabaseCollection parties;
    private DatabaseCollection partiesInvitations;
    private DatabaseCollection partiesMembers;

    private DatabaseCollection clans;
    private DatabaseCollection clanMembers;
    private DatabaseCollection clanInvitations;

    public DKFriendStorage(Database database) {
        this.database = database;
        createCollections();
    }

    public DatabaseCollection getFriends() {
        return friends;
    }

    public DatabaseCollection getParties() {
        return parties;
    }

    public DatabaseCollection getPartiesMembers() {
        return partiesMembers;
    }

    public DatabaseCollection getPartiesInvitations() {
        return partiesInvitations;
    }

    public DatabaseCollection getFriendRequests() {
        return friendRequests;
    }

    public DatabaseCollection getClans() {
        return clans;
    }

    public DatabaseCollection getClanMembers() {
        return clanMembers;
    }

    public DatabaseCollection getClanInvitations() {
        return clanInvitations;
    }

    private void createCollections(){
        friends = database.createCollection("dkfriends_friends")
                .field("PlayerId", DataType.UUID,FieldOption.NOT_NULL)
                .field("FriendId", DataType.UUID,FieldOption.NOT_NULL)
                .field("Favorite", DataType.BOOLEAN,FieldOption.NOT_NULL)
                .field("Relation", DataType.STRING,64)
                .field("Time", DataType.LONG,FieldOption.NOT_NULL)
                .create();

        friendRequests = database.createCollection("dkfriends_friends_requests")
                .field("ReceiverId", DataType.UUID,FieldOption.NOT_NULL)
                .field("RequesterId", DataType.UUID,FieldOption.NOT_NULL)
                .field("Message", DataType.STRING)
                .field("Time", DataType.LONG,64,FieldOption.NOT_NULL)
                .create();

        parties = database.createCollection("dkfriends_parties")
                .field("Id", DataType.UUID,FieldOption.NOT_NULL)
                .field("Public", DataType.BOOLEAN,FieldOption.NOT_NULL)
                .field("Category", DataType.STRING)
                .field("Topic", DataType.STRING)
                .field("Properties", DataType.LONG_TEXT)
                .field("Time", DataType.LONG,64,FieldOption.NOT_NULL)
                .create();

        partiesInvitations = database.createCollection("dkfriends_parties_invitations")
                .field("PartyId", DataType.UUID,FieldOption.NOT_NULL)
                .field("PlayerId", DataType.UUID,FieldOption.NOT_NULL)
                .field("InviterId", DataType.UUID,FieldOption.NOT_NULL)
                .field("Time", DataType.LONG,64,FieldOption.NOT_NULL)
                .create();

        partiesMembers = database.createCollection("dkfriends_parties_members")
                .field("PartyId", DataType.UUID,FieldOption.NOT_NULL)
                .field("PlayerId", DataType.UUID,FieldOption.NOT_NULL)
                .field("Role", DataType.STRING)
                .field("Time", DataType.LONG,64,FieldOption.NOT_NULL)
                .create();


        this.clans = this.database.createCollection("dkfriends_clans")
                .field("Id", DataType.UUID, FieldOption.NOT_NULL, FieldOption.PRIMARY_KEY)
                .field("Name", DataType.STRING, FieldOption.UNIQUE)
                .field("Tag", DataType.STRING, FieldOption.UNIQUE)
                .field("Status", DataType.STRING)
                .field("Properties", DataType.LONG_TEXT, -1, "{}")
                .create();

        this.clanMembers = this.database.createCollection("dkfriends_clans_members")
                .field("ClanId", DataType.UUID, ForeignKey.of(this.clans, "Id", ForeignKey.Option.CASCADE), FieldOption.NOT_NULL)
                .field("PlayerId", DataType.UUID, FieldOption.NOT_NULL)
                .field("Role", DataType.STRING, FieldOption.NOT_NULL)
                .field("Joined", DataType.LONG, FieldOption.NOT_NULL)
                .create();

        this.clanInvitations = this.database.createCollection("dkfriends_clans_invitations")
                .field("ClanId", DataType.UUID, ForeignKey.of(this.clans, "Id", ForeignKey.Option.CASCADE), FieldOption.NOT_NULL)
                .field("PlayerId", DataType.UUID, FieldOption.NOT_NULL)
                .field("InvitedByPlayerId", DataType.UUID, FieldOption.NOT_NULL)
                .field("Time", DataType.LONG, FieldOption.NOT_NULL)
                .create();
    }

}
