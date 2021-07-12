package net.pretronic.dkfriends.minecraft.gui;

import org.mcnative.runtime.api.service.event.player.inventory.MinecraftPlayerInventoryClickEvent;
import org.mcnative.runtime.api.service.inventory.Slots;
import org.mcnative.runtime.api.service.inventory.gui.builder.ElementList;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;
import org.mcnative.runtime.api.service.inventory.gui.context.ScreenContext;
import org.mcnative.runtime.api.service.inventory.gui.element.BasicElement;
import org.mcnative.runtime.api.service.inventory.item.ItemStack;
import org.mcnative.runtime.api.service.inventory.item.material.Material;
import org.mcnative.runtime.api.text.Text;
import org.mcnative.runtime.api.text.format.TextColor;

public class Layout {

    public static <T extends ScreenContext<GuiContext>> void register(ElementList<GuiContext, T> elements){
        elements.addElement(new BasicElement<GuiContext, T>(Slots.lines(4)) {
            @Override
            protected ItemStack create(T context) {
                return ItemStack.newItemStack(Material.WHITE_STAINED_GLASS_PANE);
            }
        });

        elements.addElement(new BasicElement<GuiContext, T>(Slots.slot(45)) {
            @Override
            protected ItemStack create(T context) {
                return ItemStack.newItemStack(Material.GOLDEN_HELMET)
                        .setLore(Text.of("§7All your friends"))
                        .setGlowing(context.getPage().getName().equals("friendPage"))
                        .setDisplayName(Text.of("§eFriends"));
            }

            @Override
            public void handleClick(T context, MinecraftPlayerInventoryClickEvent event) {
                if(context.getPage().getName().equals("friendPage")) return;
                context.root().getGui().openScreen(context.root().getPlayer(), "friends");
            }
        });

        elements.addElement(new BasicElement<GuiContext, T>(Slots.slot(46)) {
            @Override
            protected ItemStack create(T context) {
                return ItemStack.newItemStack(Material.IRON_CHESTPLATE)
                        .setLore(Text.of("§7All your clan members"))
                        .setGlowing(context.getPage().getName().equals("clanPage"))
                        .setDisplayName(Text.of("§bClans"));
            }

            @Override
            public void handleClick(T context, MinecraftPlayerInventoryClickEvent event) {
                if(context.getPage().getName().equals("clanPage")) return;
                context.root().getGui().open(context.root().getPlayer(), "clanPage");
            }
        });

        elements.addElement(new BasicElement<GuiContext, T>(Slots.slot(47)) {
            @Override
            protected ItemStack create(T context) {
                return ItemStack.newItemStack(Material.FIREWORK_ROCKET)
                        .setLore(Text.of("§7Parties"))
                        .setGlowing(context.getPage().getName().equals("partyPage"))
                        .setDisplayName(Text.of("§eParties"));
            }

            @Override
            public void handleClick(T context, MinecraftPlayerInventoryClickEvent event) {
                if(context.getPage().getName().equals("partyPage")) return;
                context.root().getGui().open(context.root().getPlayer(), "partyPage");
            }
        });

        elements.addElement(new BasicElement<GuiContext, T>(Slots.slot(48)) {
            @Override
            protected ItemStack create(T context) {
                return ItemStack.newItemStack(Material.COMPARATOR)
                        .setLore(Text.of("Settings", TextColor.RED))
                        .setGlowing(context.getPage().getName().equals("settingsPage"))
                        .setDisplayName(Text.of("Settings", TextColor.RED));
            }

            @Override
            public void handleClick(T context, MinecraftPlayerInventoryClickEvent event) {
                if(context.getPage().getName().equals("settingsPage")) return;
                context.root().getGui().open(context.root().getPlayer(), "settingsPage");
            }
        });
    }
}
