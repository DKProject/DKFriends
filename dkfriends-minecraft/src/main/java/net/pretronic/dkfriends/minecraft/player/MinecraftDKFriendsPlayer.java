package net.pretronic.dkfriends.minecraft.player;

import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.settings.PlayerActionCheck;
import net.pretronic.dkfriends.api.player.settings.PlayerSettings;
import net.pretronic.dkfriends.common.DefaultDKFriends;
import net.pretronic.dkfriends.common.player.DefaultDKFriendsPlayer;
import net.pretronic.dkfriends.minecraft.config.DKFriendsConfig;
import net.pretronic.libraries.document.Document;
import net.pretronic.libraries.document.entry.DocumentEntry;
import net.pretronic.libraries.utility.Iterators;
import org.mcnative.runtime.api.Setting;
import org.mcnative.runtime.api.player.MinecraftPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MinecraftDKFriendsPlayer extends DefaultDKFriendsPlayer {

    private final MinecraftPlayer player;
    private final List<Setting> cachedSettings;

    public MinecraftDKFriendsPlayer(DefaultDKFriends dkFriends, UUID uniqueId, MinecraftPlayer player) {
        super(dkFriends, uniqueId);
        this.player = player;
        this.cachedSettings = new ArrayList<>();
    }

    @Override
    public boolean isOnline() {
        return player.isOnline();
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void setSetting(String key, Object value) {
        Setting setting = getSettingObject(key);
        if(setting != null)setting.setValue(value);
        else player.setSetting("DKFriends",key,value);
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
        else player.setSetting("DKFriends",key,document);
    }

    @Override
    public void setActionSetting(String key, boolean value) {
        if(value) player.setSetting("DKFriends",key,Document.newDocument());
        else{
            Document data = Document.newDocument();
            for (String name : PlayerSettings.ACTION_CHECKS.keySet()) data.set(name,false);
            player.setSetting("DKFriends",key,data);
        }
    }

    @Override
    public boolean isActionAllow(String key, DKFriendsPlayer target) {
        Setting setting = getSettingObject(key);
        if(setting == null) return true;

        Document d = setting.getDocumentValue();
        if(d.isEmpty()) return true;

        for (DocumentEntry entry : d.entries()) {
            PlayerActionCheck check = PlayerSettings.ACTION_CHECKS.get(entry.getKey());
            if(check.matches(this,target) && entry.toPrimitive().getAsBoolean()) return true;
        }
        return false;
    }

    private Setting getSettingObject(String key){
        Setting setting = Iterators.findOne(this.cachedSettings, setting1 -> setting1.getKey().equalsIgnoreCase(key));
        if(setting == null) setting = player.getSetting("DKFriends",key);
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
}
