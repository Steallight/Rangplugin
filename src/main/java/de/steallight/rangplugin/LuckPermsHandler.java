package de.steallight.rangplugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LuckPermsHandler {

    private static LuckPerms luckPerms;

    public static void setRank(Player p, String gruppe) {
        LuckPermsHandler.luckPerms.getUserManager().getUser(luckPerms.getUserManager().getUser(p.getUniqueId()).getUniqueId()).setPrimaryGroup(gruppe);
    }

    public static User getPlayer(Player p){
       User player =  luckPerms.getUserManager().getUser(p.getUniqueId());

       return player;
    }

    public static Set<Group> getAllGroups(){
       Set<Group> groups = luckPerms.getGroupManager().getLoadedGroups();
       return groups;
    }

    public static Boolean isAvailable(String groupname){
       if (luckPerms.getGroupManager().getGroup(groupname) == null){
           return false;
       }else {
           return true;
       }
    }



    static {
        luckPerms = LuckPermsProvider.get();
    }
}
