package net.pretronic.dkfriends.minecraft.commands.clan;

import net.pretronic.dkfriends.api.clan.Clan;
import net.pretronic.dkfriends.api.party.Party;
import net.pretronic.dkfriends.api.player.DKFriendsPlayer;
import net.pretronic.dkfriends.api.player.settings.PlayerSettings;
import net.pretronic.dkfriends.minecraft.commands.CommandUtil;
import net.pretronic.dkfriends.minecraft.config.Messages;
import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.player.OnlineMinecraftPlayer;

public class PartyCommand extends BasicCommand {

    public PartyCommand(ObjectOwner owner) {
        super(owner, CommandConfiguration.name("party"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        DKFriendsPlayer player = ((MinecraftPlayer)sender).getAs(DKFriendsPlayer.class);

        if(CommandUtil.isInClanCheck(sender, player)) {

            Party party = player.getParty();
            if(party != null){
                if(!party.canInvite(player.getId())){
                    sender.sendMessage(Messages.ERROR_PARTY_NOT_ALLOWED);
                    return;
                }
            }else party = player.createParty();

            Clan clan = player.getClan();
            boolean no = true;
            for (OnlineMinecraftPlayer online : McNative.getInstance().getNetwork().getOnlinePlayers()) {
                if(clan.isMember(online.getUniqueId()) && online.getAs(DKFriendsPlayer.class).isActionAllow(PlayerSettings.CLAN_ALLOW_MESSAGES,player)){
                    no = false;
                    party.invite(player,online.getUniqueId());
                    sender.sendMessage(Messages.COMMAND_PARTY_INVITED, VariableSet.create()
                            .addDescribed("player",online));
                }
            }

            if(no){
                sender.sendMessage(Messages.COMMAND_CLAN_PARTY_NO_ONLINE);
            }
        }

    }
}
