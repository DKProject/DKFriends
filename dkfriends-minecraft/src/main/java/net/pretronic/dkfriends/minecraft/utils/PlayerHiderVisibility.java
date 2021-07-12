package net.pretronic.dkfriends.minecraft.utils;

import net.pretronic.dkfriends.minecraft.config.Messages;
import org.mcnative.runtime.api.service.inventory.item.ItemStack;
import org.mcnative.runtime.api.service.inventory.item.material.Material;
import org.mcnative.runtime.api.text.components.MessageComponent;

public enum PlayerHiderVisibility {

    ALL(Material.LIME_DYE, Messages.LOBBY_HIDER_ALL_DISPLAY_NAME),
    VIP(Material.PURPLE_DYE, Messages.LOBBY_HIDER_VIP_DISPLAY_NAME),
    NONE(Material.RED_DYE, Messages.LOBBY_HIDER_NONE_DISPLAY_NAME);

    private final Material material;
    private final MessageComponent<?> displayName;

    PlayerHiderVisibility(Material material, MessageComponent<?> displayName) {
        this.material = material;
        this.displayName = displayName;
    }

    public ItemStack buildItem() {
        ItemStack itemStack = ItemStack.newItemStack(this.material);
        itemStack.setDisplayName(this.displayName);
        return itemStack;
    }

    public static PlayerHiderVisibility parse(String name) {
        for (PlayerHiderVisibility value : values()) {
            if(value.name().equalsIgnoreCase(name)) return value;
        }
        throw new IllegalArgumentException("Can't match PlayerHidingType " + name);
    }
}
