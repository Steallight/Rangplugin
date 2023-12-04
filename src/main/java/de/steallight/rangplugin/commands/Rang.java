package de.steallight.rangplugin.commands;

import de.steallight.rangplugin.LuckPermsHandler;
import de.steallight.rangplugin.RangPlugin;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Rang implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (cmd.getName().equalsIgnoreCase("rang")) {
                Player p = (Player) sender;
                if (p.hasPermission("rang.set") || p.isOp()) {
                    if (args.length == 2) {
                        String groupname = args[1];
                        String playername = args[0];
                        if (Bukkit.getPlayer(playername) == null) {
                            p.sendMessage(RangPlugin.prefix + "§cDieser Spieler existiert nicht!");
                        } else if (playername.equals(p.getName())) {
                            p.sendMessage(RangPlugin.prefix + "§7Du kannst dich §c§lnicht §7selber in eine Gruppe setzen!");
                        } else if (!LuckPermsHandler.isAvailable(groupname)) {
                            p.sendMessage("§7Die Gruppe existiert §c§lnicht§7!");
                        } else {
                            Player pr = Bukkit.getPlayer(playername);
                            User player = LuckPermsHandler.getPlayer(pr);

                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                    "lp user " + playername + " parent set " + groupname);
                            pr.kickPlayer("§7Neuer Rang: §c" + groupname);
                            p.sendMessage(RangPlugin.prefix + "§aDer Spieler §c" + playername + "§a wurde erfolgreich zur Gruppe§c " + groupname + "§a hinzugefügt!");

                        }
                    } else {
                        p.sendMessage(RangPlugin.prefix + "§cBenutze: §c§l/rang [Spieler] [Rang]");
                    }
                } else {
                    p.sendMessage(RangPlugin.prefix + "§cDazu hast du keine Rechte!");
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("rang")) {


            if (args.length == 2) {
                String groupname = args[1];
                String playername = args[0];
                if (Bukkit.getPlayer(playername) == null) {
                    Bukkit.getConsoleSender().sendMessage(RangPlugin.prefix + "§cDieser Spieler existiert nicht!");
                } else if (!LuckPermsHandler.isAvailable(groupname)) {
                    Bukkit.getConsoleSender().sendMessage("§7Die Gruppe existiert §c§lnicht§7!");
                } else {
                    Player pr = Bukkit.getPlayer(playername);
                    User player = LuckPermsHandler.getPlayer(pr);


                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            "lp user " + playername + " parent set " + groupname);
                    pr.kickPlayer("§7Neuer Rang: §c" + groupname);
                    Bukkit.getConsoleSender().sendMessage(RangPlugin.prefix + "§aDer Spieler §c" + playername + "§a wurde erfolgreich zur Gruppe§c " + groupname + "§a hinzugefügt!");

                }
            } else {
                Bukkit.getConsoleSender().sendMessage(RangPlugin.prefix + "§cBenutze: §c§l/rang [Spieler] [Rang]");
            }

        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> tabComplete = new ArrayList<>();
        if (args.length == 0) return tabComplete;
        if (args.length == 1) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                tabComplete.add(all.getName());
            }

        } else if (args.length == 2) {

            Set<Group> groups = LuckPermsHandler.getAllGroups();


            for (Group group : groups) {
                tabComplete.add(group.getName());
            }
        }
        ArrayList<String> completerList = new ArrayList<>();
        String currentarg = args[args.length - 1].toLowerCase();
        for (String s : tabComplete) {
            String s1 = s.toLowerCase();
            if (s1.startsWith(currentarg)) {
                completerList.add(s);
            }
        }


        return completerList;
    }
}

