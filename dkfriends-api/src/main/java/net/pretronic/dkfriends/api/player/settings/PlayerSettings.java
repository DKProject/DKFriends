package net.pretronic.dkfriends.api.player.settings;

import java.util.HashMap;
import java.util.Map;

public class PlayerSettings {

    public static final Map<String,PlayerActionCheck> ACTION_CHECKS = new HashMap<>();

    public static final String DESIGN = "design";

    public static final String FRIEND_NOTIFICATIONS = "friend.notifications";//
    public static final String FRIEND_ALLOW_MESSAGES = "friend.allow.messages";//
    public static final String FRIEND_ALLOW_JUMP = "friend.allow.jump";//
    public static final String FRIEND_ALLOW_REQUESTS = "friend.allow.requests";//
    public static final String FRIEND_PLAYER_HIDER_VISIBILITY = "friend.player.hider.visibility";

    public static final String PARTY_ALLOW_INVITATIONS = "party.allow.invitations";

    public static final String CLAN_NOTIFICATIONS = "clan.notifications";
    public static final String CLAN_ALLOW_MESSAGES = "clan.allow.messages";
    public static final String CLAN_ALLOW_JUMP = "clan.allow.jump";
}
