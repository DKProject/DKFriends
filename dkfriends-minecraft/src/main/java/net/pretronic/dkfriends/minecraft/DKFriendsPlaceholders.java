package net.pretronic.dkfriends.minecraft;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
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
        }
        return null;
    }
}
