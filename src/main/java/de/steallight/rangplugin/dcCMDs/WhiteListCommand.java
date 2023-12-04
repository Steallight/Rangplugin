package de.steallight.rangplugin.dcCMDs;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class WhiteListCommand extends ListenerAdapter {


    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
        if (e.getName().equals("whitelist")) {
            String minecraftname = e.getOption("minecraftname").getAsString();
            String plattform = e.getOption("platform").getAsString();
            OfflinePlayer player = Bukkit.getOfflinePlayer(minecraftname);

            if (!player.isWhitelisted()) {
                TextChannel tc = e.getGuild().getTextChannelById("802542719926272000");



                EmbedBuilder eb = new EmbedBuilder();

                eb
                        .setTitle("Neuer Whitelistrequest")
                        .setColor(Color.MAGENTA)
                        .addField("Plattform", plattform, false)
                        .addField("User-ID", e.getUser().getId(), false)
                        .addField("Minecraft-Name", minecraftname, false)
                        .setThumbnail(e.getUser().getAvatarUrl());

                    Button acceptButton = Button.success("request-accept[" + minecraftname + "]_" + e.getUser().getId() +";" + plattform, "Annehmen");
                Button denyButton = Button.danger("request-deny", "Ablehnen");

                tc.sendMessageEmbeds(eb.build()).setActionRow(acceptButton, denyButton).queue();
                e.reply("Deine Request liegt dem Mod-Team vor!").setEphemeral(true).queue();
            }else {
                e.reply("Du bist schon gewhitelisted").setEphemeral(true).queue();
            }
        }
    }


}
