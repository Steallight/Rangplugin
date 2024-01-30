package de.steallight.rangplugin;

import de.steallight.rangplugin.commands.Rang;
import net.dv8tion.jda.api.JDA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

public final class RangPlugin extends JavaPlugin {

    private JDA jda;

    private static RangPlugin INSTANCE;
    public static String prefix;

    public void config() {


        File c = new File("plugins/RangPlugin", "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(c);
        if (!c.exists()) {

            cfg.options().copyDefaults(true);
            cfg.addDefault("prefix", "&8[&c&bRang&8] ");
            prefix = ChatColor.translateAlternateColorCodes('&', cfg.getString("prefix"));


        }
    }



    @Override
    public void onEnable() {

        config();
        saveDefaultConfig();
        INSTANCE = this;


        System.out.println(Arrays.toString(getServer().getPluginManager().getPlugins()));
        if (Arrays.toString(getServer().getPluginManager().getPlugins()).contains("LuckPerms")) {
            getCommand("rang").setExecutor(new Rang());
            getCommand("rang").setTabCompleter(new Rang());


            Bukkit.getConsoleSender().sendMessage(prefix + "§7Plugin erfolgreich §aaktiviert!");
        } else {
            Bukkit.getConsoleSender().sendMessage(prefix + "§cLuckPerms fehlt! Server konnte nicht gestartet werden!");
            this.getPluginLoader().disablePlugin(this);
        }


    }


    @Override
    public void onDisable() {


        Bukkit.getConsoleSender().sendMessage(prefix + "§7Plugin erfolgreich §cdeaktiviert!");


    }




public static RangPlugin getPlugin(){
        return INSTANCE;
}


}
