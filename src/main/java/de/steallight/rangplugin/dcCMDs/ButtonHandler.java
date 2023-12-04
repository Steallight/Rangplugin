package de.steallight.rangplugin.dcCMDs;

import de.steallight.rangplugin.RangPlugin;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class ButtonHandler extends ListenerAdapter {
    public static String minecraftname;
    private final RangPlugin plugin;

    public ButtonHandler(RangPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent e) {
        if (e.getComponentId().startsWith("request-accept")) {
            String outputData = e.getComponentId();
            System.out.println(outputData);
            TextChannel tc = e.getGuild().getTextChannelById("1156609489055793202");
            minecraftname = "";
            String seperator = "_";
            String seperatorPlatform = ";";
            int startIndex = outputData.indexOf("[");
            int endIndex = outputData.indexOf("]");
            int startIndex2 = outputData.indexOf("]_");
            int endIndex2 = outputData.indexOf(";");

            int sepPlatformPos = outputData.indexOf(seperatorPlatform);
            String plattform = outputData.substring(sepPlatformPos + seperatorPlatform.length()).trim();


            String UserId = outputData.substring(startIndex2 +2, endIndex2);
            System.out.println(UserId);
            minecraftname = e.getComponentId().substring(startIndex + 1, endIndex);

             System.out.println(minecraftname);
            OfflinePlayer player = Bukkit.getOfflinePlayer(minecraftname);
            Member addedMember = e.getGuild().retrieveMemberById(UserId).complete();
            System.out.println(addedMember);
            if (!player.isWhitelisted()) {


                EmbedBuilder eb = new EmbedBuilder();
                eb
                        .setTitle("Whitelist-Log")
                        .setColor(Color.GREEN)
                        .addField("Plattform", plattform, false)
                        .addField("MC-Name", minecraftname, false)
                        .addBlankField(false)
                        .addField("User", addedMember.getAsMention(), false)
                        .addField("User-ID", UserId, false)
                        .setFooter("genehmigt von " + e.getMember().getEffectiveName());
                new WhitelistPlayer().runTask(plugin);
                tc.sendMessageEmbeds(eb.build()).queue();
                e.reply("Der User wurde gewhitelisted!").setEphemeral(true).queue();
                e.getMessage().delete().queue();
                addedMember.getGuild().addRoleToMember(addedMember, e.getGuild().getRoleById("1161770704254017596")).queue();

                return;
            } else {
                e.reply("Dieser User ist schon gewhitelisted!").setEphemeral(true).queue();
                e.getMessage().delete().queue();
                return;
            }
        } else if (e.getComponentId().equals("request-deny")) {
            e.reply("Request wurde abgelehnt").setEphemeral(true).queue();
            e.getMessage().delete().queue();
        }
    }

    public class WhitelistPlayer extends BukkitRunnable {

        @Override
        public void run() {
            OfflinePlayer player = Bukkit.getOfflinePlayer(minecraftname);
            player.setWhitelisted(true);
        }
    }


}
