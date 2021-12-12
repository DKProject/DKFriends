package net.pretronic.dkfriends.minecraft;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.clan.ClanMember;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.party.PartyMember;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.friend.Friend;
import net.pretronic.dkfriends.minecraft.config.DKFriendsConfig;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.serviceprovider.placeholder.PlaceholderHook;

public class DKFriendsPlaceholders implements PlaceholderHook {

    @Override
    public Object onRequest(MinecraftPlayer player, String parameter) {
        DKFriendsPlayer friendsPlayer = player.getAs(DKFriendsPlayer.class);
        if(parameter.equalsIgnoreCase("player_name")){
            return player.getName();
        }else if(parameter.equalsIgnoreCase("player_uniqueId")){
            return player.getUniqueId();
        }else if(parameter.equalsIgnoreCase("player_friends_max")){
            return friendsPlayer.getMaxFriends();
        }else if(parameter.equalsIgnoreCase("player_friends_list")){
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Friend member : friendsPlayer.getFriends()) {
                if(first) first = false;
                else result.append(", ");
                result.append(member.getPlayer().getDisplayName());
            }
            return result.toString();
        }else if(parameter.equalsIgnoreCase("player_friends_list_online")){
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Friend member : friendsPlayer.getOnlineFriends()) {
                if(first) first = false;
                else result.append(", ");
                result.append(member.getPlayer().getDisplayName());
            }
            return result.toString();
        }else if(parameter.equalsIgnoreCase("player_friends_list_offline")){
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Friend member : friendsPlayer.getOfflineFriends()) {
                if(first) first = false;
                else result.append(", ");
                result.append(member.getPlayer().getDisplayName());
            }
            return result.toString();
        }else if(parameter.equalsIgnoreCase("player_friends_count")){
            return friendsPlayer.getFriends().size();
        }else if(parameter.equalsIgnoreCase("player_party_has")){
            return friendsPlayer.isInParty();
        }else if(parameter.equalsIgnoreCase("player_party_topic")){
            Party party = friendsPlayer.getParty();
            if(party != null) return party.getTopic();
            return "";
        }else if(parameter.equalsIgnoreCase("player_party_category")){
            Party party = friendsPlayer.getParty();
            if(party != null) return party.getCategory();
            return "";
        }else if(parameter.equalsIgnoreCase("player_party_leader")){
            Party party = friendsPlayer.getParty();
            if(party != null) return party.getLeader().getPlayer().getDisplayName();
            return "";
        }else if(parameter.equalsIgnoreCase("player_party_max")){
            Party party = friendsPlayer.getParty();
            if(party != null) return party.getMaxSize();
            return "";
        }else if(parameter.equalsIgnoreCase("player_party_count")){
            Party party = friendsPlayer.getParty();
            if(party != null) return party.getMembers().size();
            return "";
        }else if(parameter.equalsIgnoreCase("player_party_members")){
            Party party = friendsPlayer.getParty();
            if(party != null) {
                StringBuilder result = new StringBuilder();
                boolean first = true;
                for (PartyMember member : party.getMembers()) {
                    if(first) first = false;
                    else result.append(", ");
                    result.append(member.getPlayer().getDisplayName());
                }
                return result.toString();
            }
            return "";
        }else if(parameter.equalsIgnoreCase("player_party_members_online")){
            Party party = friendsPlayer.getParty();
            if(party != null) {
                StringBuilder result = new StringBuilder();
                boolean first = true;
                for (PartyMember member : party.getOnlineMembers()) {
                    if(first) first = false;
                    else result.append(", ");
                    result.append(member.getPlayer().getDisplayName());
                }
                return result.toString();
            }
            return "";
        }else if(parameter.equalsIgnoreCase("player_party_members_offline")){
            Party party = friendsPlayer.getParty();
            if(party != null) {
                StringBuilder result = new StringBuilder();
                boolean first = true;
                for (PartyMember member : party.getOfflineMembers()) {
                    if(first) first = false;
                    else result.append(", ");
                    result.append(member.getPlayer().getDisplayName());
                }
                return result.toString();
            }
            return "";
        }else if(parameter.equalsIgnoreCase("player_clan_has")){
            Clan clan = friendsPlayer.getClan();
            return clan != null;
        }else if(parameter.equalsIgnoreCase("player_clan_name")){
            Clan clan = friendsPlayer.getClan();
            if(clan != null) return clan.getName();
            return "";
        }else if(parameter.equalsIgnoreCase("player_clan_name_formatted")){
            Clan clan = friendsPlayer.getClan();
            if(clan != null) return DKFriendsConfig.CLAN_NAME_FORMATTED.replace("{tag}",clan.getName());
            return "";
        }else if(parameter.equalsIgnoreCase("player_clan_tag")){
            Clan clan = friendsPlayer.getClan();
            if(clan != null) return clan.getTag();
            return "";
        }else if(parameter.equalsIgnoreCase("player_clan_tag_formatted")){
            Clan clan = friendsPlayer.getClan();
            if(clan != null) return DKFriendsConfig.CLAN_TAG_FORMATTED.replace("{tag}",clan.getTag());
            return "";
        }else if(parameter.equalsIgnoreCase("player_clan_count")){
            Clan clan = friendsPlayer.getClan();
            if(clan != null) return clan.getMembers().size();
            return 0;
        }else if(parameter.equalsIgnoreCase("player_clan_members")){
            Clan clan = friendsPlayer.getClan();
            if(clan != null) {
                StringBuilder result = new StringBuilder();
                boolean first = true;
                for (ClanMember member : clan.getMembers()) {
                    if(first) first = false;
                    else result.append(", ");
                    result.append(member.getPlayer().getDisplayName());
                }
                return result.toString();
            }
            return "";
        }else if(parameter.equalsIgnoreCase("player_clan_members_online")){
            Clan clan = friendsPlayer.getClan();
            if(clan != null) {
                StringBuilder result = new StringBuilder();
                boolean first = true;
                for (ClanMember member : clan.getOnlineMembers()) {
                    if(first) first = false;
                    else result.append(", ");
                    result.append(member.getPlayer().getDisplayName());
                }
                return result.toString();
            }
            return "";
        }else if(parameter.equalsIgnoreCase("player_clan_members_offline")){
            Clan clan = friendsPlayer.getClan();
            if(clan != null) {
                StringBuilder result = new StringBuilder();
                boolean first = true;
                for (ClanMember member : clan.getOfflineMembers()) {
                    if(first) first = false;
                    else result.append(", ");
                    result.append(member.getPlayer().getDisplayName());
                }
                return result.toString();
            }
            return "";
        }
        return null;
    }
}
