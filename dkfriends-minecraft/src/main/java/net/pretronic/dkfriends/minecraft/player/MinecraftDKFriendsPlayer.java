package net.pretronic.dkfriends.minecraft.player;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.settings.PlayerActionCheck;
import net.pretronic.dkfriends.api.player.settings.PlayerSettings;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.common.player.DefaultDKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.config.DKFriendsConfig;
import net.pretronic.dkfriends.minecraft.utils.PlayerHiderVisibility;
import net.pretronic.libraries.document.Document;
import net.pretronic.libraries.document.entry.DocumentEntry;
import net.pretronic.libraries.utility.Iterators;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.Setting;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.player.OnlineMinecraftPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MinecraftDKFriendsPlayer extends DefaultDKFriendsPlayer {

    private final UUID uniqueId;
    private final List<Setting> cachedSettings;

    public MinecraftDKFriendsPlayer(DefaultDKFriends dkfriends,UUID uniqueId) {
        super(dkfriends, uniqueId);
        this.uniqueId = uniqueId;
        this.cachedSettings = new ArrayList<>();
    }

    public MinecraftPlayer getMinecraftPlayer(){
        //@Todo Optimize
        return McNative.getInstance().getPlayerManager().getPlayer(this.uniqueId);
    }

    @Override
    public String getName() {
        return getMinecraftPlayer().getName();
    }

    @Override
    public boolean isOnline() {
        return getMinecraftPlayer() instanceof OnlineMinecraftPlayer;
    }

    @Override
    public boolean hasPermission(String permission) {
        return getMinecraftPlayer().hasPermission(permission);
    }

    @Override
    public void setSetting(String key, Object value) {
        Setting setting = getSettingObject(key);
        if(setting != null)setting.setValue(value);
        else getMinecraftPlayer().setSetting("DKFriends",key,value);
    }

    @Override
    public Object getSetting(String key) {
        Setting setting =  getSettingObject(key);
        return setting != null ? setting.getValue() : null;
    }

    @Override
    public void setActionSetting(String key, String group, boolean value) {
        Setting setting = getSettingObject(key);
        Document document = setting != null ? setting.getDocumentValue() : Document.newDocument();
        document.set(group,value);
        if(setting != null) setting.setValue(document);
        else getMinecraftPlayer().setSetting("DKFriends",key,document);
    }

    @Override
    public void setActionSetting(String key, boolean value) {
        if(value) getMinecraftPlayer().setSetting("DKFriends",key,Document.newDocument());
        else{
            Document data = Document.newDocument();
            for (String name : PlayerSettings.ACTION_CHECKS.keySet()) data.set(name,false);
            getMinecraftPlayer().setSetting("DKFriends",key,data);
        }
    }

    @Override
    public boolean getActionSetting(String key, String group) {
        Setting setting = getSettingObject(key);
        if(setting == null) return true;

        Document d = setting.getDocumentValue();
        if(d.isEmpty()) return false;
        return d.getBoolean(group);
    }

    @Override
    public boolean isActionAllow(String key, DKFriendsPlayer target) {
        Setting setting = getSettingObject(key);
        if(setting == null) return true;

        Document d = setting.getDocumentValue();
        if(d.isEmpty()) return true;

        for (DocumentEntry entry : d.entries()) {
            PlayerActionCheck check = PlayerSettings.ACTION_CHECKS.get(entry.getKey());
            if(check != null && check.matches(this,target) && entry.toPrimitive().getAsBoolean()) return true;
        }
        return false;
    }

    private Setting getSettingObject(String key){
        Setting setting = Iterators.findOne(this.cachedSettings, setting1 -> setting1.getKey().equalsIgnoreCase(key));
        if(setting == null) setting = getMinecraftPlayer().getSetting("DKFriends",key);
        return setting;
    }

    @Override
    public int getMaxPartySize() {
        int result = 5;
        for (Map.Entry<String,Integer> entry : DKFriendsConfig.PERMISSIONS_MAX_PARTY_SIZE.entrySet()) {
            if(entry.getKey().equals("default") || hasPermission(entry.getKey())){
                result = entry.getValue();
            }
        }
        return result;
    }

    @Override
    public int getMaxFriends() {
        int result = 5;
        for (Map.Entry<String,Integer> entry : DKFriendsConfig.PERMISSIONS_MAX_FRIENDS.entrySet()) {
            if(entry.getKey().equals("default") || hasPermission(entry.getKey())){
                result = entry.getValue();
            }
        }
        return result;
    }

    public PlayerHiderVisibility getPlayerHidingType() {
        Setting setting = getSettingObject(PlayerSettings.FRIEND_PLAYER_HIDER_VISIBILITY);
        if(setting == null || setting.getValue() == null) {
            setPlayerHidingType(PlayerHiderVisibility.ALL);
            return getPlayerHidingType();
        }
        return PlayerHiderVisibility.parse(setting.getValue());
    }

    public PlayerHiderVisibility setPlayerHidingType(PlayerHiderVisibility hidingType) {
        setSetting(PlayerSettings.FRIEND_PLAYER_HIDER_VISIBILITY, hidingType.name());
        return hidingType;
    }

    public PlayerHiderVisibility setNextPlayerHidingType() {
        PlayerHiderVisibility hidingType = getPlayerHidingType();
        switch (hidingType) {
            case ALL: return setPlayerHidingType(PlayerHiderVisibility.VIP);
            case VIP: return setPlayerHidingType(PlayerHiderVisibility.NONE);
            case NONE: return setPlayerHidingType(PlayerHiderVisibility.ALL);
        }
        throw new IllegalArgumentException("Can't set next PlayerHidingType for " + hidingType);
    }
}
