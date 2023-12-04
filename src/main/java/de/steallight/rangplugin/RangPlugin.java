package de.steallight.rangplugin;

import de.steallight.rangplugin.commands.AutoCompleteListener;
import de.steallight.rangplugin.commands.Rang;
import de.steallight.rangplugin.dcCMDs.ButtonHandler;
import de.steallight.rangplugin.dcCMDs.WhiteListCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class RangPlugin extends JavaPlugin {

    private JDA jda;
    public static String prefix;

    public void config() {


        File c = new File("plugins/RangPlugin", "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(c);
        if (!c.exists()) {

            cfg.options().copyDefaults(true);
            cfg.addDefault("prefix", "&8[&c&bRang&8] ");
            cfg.addDefault("BOT_TOKEN", "");
            prefix = ChatColor.translateAlternateColorCodes('&', cfg.getString("prefix"));


        }
    }


    @Override
    public void onEnable() {

        config();
        saveDefaultConfig();
        try {



            String discordToken = getConfig().getString("BOT_TOKEN");

            getCommand("rang").setExecutor(new Rang());
            getCommand("rang").setTabCompleter(new Rang());


            Bukkit.getConsoleSender().sendMessage(prefix + "§7Plugin erfolgreich §aaktiviert!");
            if (discordToken == null) {

                getServer().shutdown();
                Bukkit.getConsoleSender().sendMessage("§cKein DiscordBot-Token vorhanden!");
            } else {


                    this.jda = JDABuilder.createDefault(discordToken).build();

                    Guild server = jda.awaitReady().getGuildById("645388418062221312");
                    Bukkit.getConsoleSender().sendMessage(prefix + "§7Discord Bot §averbunden!");
                    assert server != null;
                    this.updateCommands(server);
                    this.addEvents();


            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {

        String discordToken = getConfig().getString("BOT_TOKEN");
        if (discordToken == null){
            Bukkit.getConsoleSender().sendMessage(prefix + "§7Plugin erfolgreich §cdeaktiviert!");
        }else {
            jda.shutdown();
            Bukkit.getConsoleSender().sendMessage(prefix + "§7Plugin erfolgreich §cdeaktiviert!");
        }

    }


    public void addEvents() {
        jda.addEventListener(new WhiteListCommand());
        jda.addEventListener(new ButtonHandler(this));
        jda.addEventListener(new AutoCompleteListener());
    }


    public void updateCommands(Guild server) {
        server.updateCommands()
                .addCommands(
                        Commands.slash("whitelist", "Frage einen Whitelist-Request an")
                                .addOption(OptionType.STRING, "minecraftname", "Gebe hier deinen Ingame Namen ein", true)
                                .addOption(OptionType.STRING, "platform", "Trage hier bitte deine Minecraft Plattform an", true, true)
                                .setDefaultPermissions(DefaultMemberPermissions.ENABLED)
                                .setGuildOnly(true)

                ).queue();
    }


}
