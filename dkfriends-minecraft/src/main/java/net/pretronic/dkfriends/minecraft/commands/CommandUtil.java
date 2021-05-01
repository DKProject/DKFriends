package net.pretronic.dkfriends.minecraft.commands;

import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.player.OnlineMinecraftPlayer;
import org.mcnative.runtime.api.text.components.MessageComponent;

import java.util.Collection;

public class CommandUtil {

    public static boolean isPlayerCheck(CommandSender sender, MessageComponent<?> prefix){
        if(sender instanceof OnlineMinecraftPlayer) return true;
        sender.sendMessage(Messages.ERROR_ONLY_PLAYER, VariableSet.create()
                .addDescribed("prefix",prefix));
        return false;
    }

    public static boolean isSelfCheck(CommandSender sender,String target){
        if(sender.getName().equalsIgnoreCase(target)){
            sender.sendMessage(Messages.ERROR_PLAYER_NOT_SELF);
            return true;
        }
        return false;
    }

    public static MinecraftPlayer getPlayer(CommandSender sender, MessageComponent<?> prefix, String name){
        MinecraftPlayer player = McNative.getInstance().getPlayerManager().getPlayer(name);
        if(player == null) sender.sendMessage(Messages.ERROR_PLAYER_NOT_FOUND, VariableSet.create()
                .addDescribed("name",name)
                .addDescribed("prefix",prefix));
        return player;
    }

    public static String readStringFromArguments(String[] arguments, int start){
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < arguments.length; i++){
            builder.append(' ').append(arguments[i]);
        }
        return builder.substring(1);
    }

}
