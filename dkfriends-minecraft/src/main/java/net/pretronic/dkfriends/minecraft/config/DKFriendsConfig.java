package net.pretronic.dkfriends.minecraft.config;

import net.pretronic.dkfriends.api.player.settings.PlayerSettings;
import net.pretronic.dkfriends.api.player.settings.checks.*;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.document.annotations.DocumentKey;
import net.pretronic.libraries.document.annotations.OnDocumentConfigurationLoad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DKFriendsConfig {

    public static List<String> SETTINGS_ACTION_GROUPS = new ArrayList<>();

    @DocumentKey("permissions.maxFriends")
    public static Map<String,Integer> PERMISSIONS_MAX_FRIENDS = new HashMap<>();

    @DocumentKey("permissions.maxPartySize")
    public static Map<String,Integer> PERMISSIONS_MAX_PARTY_SIZE = new HashMap<>();

    public static CommandConfiguration COMMAND_FRIEND = CommandConfiguration.newBuilder()
            .name("friend")
            .aliases("friends")
            .permission("dkfriends.command.friend")
            .create();

    public static CommandConfiguration COMMAND_PARTY = CommandConfiguration.newBuilder()
            .name("party")
            .aliases("parties")
            .permission("dkfriends.command.party")
            .create();

    public static CommandConfiguration COMMAND_CLAN = CommandConfiguration.newBuilder()
            .name("clan")
            .permission("dkfriends.command.clan")
            .create();

    public static boolean PLAYER_HIDER_ENABLED = true;
    public static int PLAYER_HIDER_SLOT = 1;

    public static boolean PROFILE_SKULL_ENABLED = true;
    public static int PROFILE_SKULL_SLOT = 8;

    static {
        SETTINGS_ACTION_GROUPS.add("friend");
        SETTINGS_ACTION_GROUPS.add("favorite");
        SETTINGS_ACTION_GROUPS.add("party");
        SETTINGS_ACTION_GROUPS.add("clan");
        SETTINGS_ACTION_GROUPS.add("premium-dkfriends.group.premium");
        SETTINGS_ACTION_GROUPS.add("youtuber-dkfriends.group.youtuber");
        SETTINGS_ACTION_GROUPS.add("team-dkfriends.group.team");

        PERMISSIONS_MAX_FRIENDS.put("default",250);
        PERMISSIONS_MAX_FRIENDS.put("dkfriends.friend.max.500",500);
        PERMISSIONS_MAX_FRIENDS.put("dkfriends.friend.max.750",750);
        PERMISSIONS_MAX_FRIENDS.put("dkfriends.friend.max.1000",1000);

        PERMISSIONS_MAX_PARTY_SIZE.put("default",10);
        PERMISSIONS_MAX_PARTY_SIZE.put("dkfriends.party.maxSize.25",25);
        PERMISSIONS_MAX_PARTY_SIZE.put("dkfriends.party.maxSize.50",50);
        PERMISSIONS_MAX_PARTY_SIZE.put("dkfriends.party.maxSize.100",100);
    }


    @OnDocumentConfigurationLoad
    public static void onLoad(){
        for (String name : SETTINGS_ACTION_GROUPS) {
            if(name.equals("friend")) PlayerSettings.ACTION_CHECKS.put("friend",new FriendPlayerActionCheck());
            else if(name.equals("favorite")) PlayerSettings.ACTION_CHECKS.put("favorite",new ClanPlayerActionCheck());
            else if(name.equals("party")) PlayerSettings.ACTION_CHECKS.put("party",new PartyPlayerActionCheck());
            else if(name.equals("clan")) PlayerSettings.ACTION_CHECKS.put("clan",new FavoritePlayerActionCheck());
            else{
                String[] parts = name.split("-");
                PlayerSettings.ACTION_CHECKS.put(parts[0],new PermissionActionCheck(parts[1]));
            }
        }
    }
}
