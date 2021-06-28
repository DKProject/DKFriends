package net.pretronic.dkfriends.minecraft.gui;

import net.pretronic.dkfriends.minecraft.gui.pages.FriendPageContext;
import org.mcnative.runtime.api.service.inventory.Slots;
import org.mcnative.runtime.api.service.inventory.gui.builder.ElementList;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;
import org.mcnative.runtime.api.service.inventory.gui.element.BasicElement;
import org.mcnative.runtime.api.service.inventory.item.ItemStack;
import org.mcnative.runtime.api.service.inventory.item.material.Material;

public class Layout {

    public static void register(ElementList<GuiContext, FriendPageContext> elements){
        elements.addElement(new BasicElement<GuiContext, FriendPageContext>(Slots.lines(4)) {
            @Override
            protected ItemStack create(FriendPageContext context) {
                return ItemStack.newItemStack(Material.GLASS);
            }
        });

        elements.addElement(new BasicElement<GuiContext, FriendPageContext>(Slots.slot(45)) {
            @Override
            protected ItemStack create(FriendPageContext context) {
                return ItemStack.newItemStack(Material.GOLDEN_HELMET);
                       // .setLore("§7All your friends")
                        //.setGlowing(context.getPage().getName().equals("friends"))
                    //    .setDisplayName("§eFriends");
            }
        });

        elements.addElement(new BasicElement<GuiContext, FriendPageContext>(Slots.slot(46)) {
            @Override
            protected ItemStack create(FriendPageContext context) {
                return ItemStack.newItemStack(Material.IRON_CHESTPLATE);
                       // .setLore("§7All your clan members")
                    //    .setGlowing(context.getPage().getName().equals("clans"))
                    //    .setDisplayName("§bClans");
            }
        });

        elements.addElement(new BasicElement<GuiContext, FriendPageContext>(Slots.slot(47)) {
            @Override
            protected ItemStack create(FriendPageContext context) {
                return ItemStack.newItemStack(Material.FIREWORK_ROCKET);
                    //    .setLore("§7Parties")
                   //     .setGlowing(context.getPage().getName().equals("parties"))
                       // .setDisplayName("§eParties");
            }
        });
    }
}
