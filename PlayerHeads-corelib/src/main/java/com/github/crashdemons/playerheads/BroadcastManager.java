/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.shininet.bukkit.playerheads.Formatter;
import org.shininet.bukkit.playerheads.Lang;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class BroadcastManager {
    private BroadcastManager(){}
    
    public static void broadcast(Plugin plugin, String message){
        plugin.getServer().broadcastMessage(message);
    }
    public static void broadcastRange(Plugin plugin, String message, Location location, int broadcastRange){
        if (broadcastRange > 0) {
            broadcastRange *= broadcastRange;
            List<Player> players = location.getWorld().getPlayers();

            for (Player loopPlayer : players) {
                try{
                    if (location.distanceSquared(loopPlayer.getLocation()) <= broadcastRange) {
                        loopPlayer.sendMessage(message);
                    }
                }catch(IllegalArgumentException e){
                    //entities are in different worlds
                }
            }
        } else {
            broadcast(plugin,message);
        }
    }
    public static void broadcastMobBehead(JavaPlugin plugin, Player killer, Entity entity){
        if (plugin.getConfig().getBoolean("broadcastmob") && killer!=null) { //mob-on-mob broadcasts would be extremely annoying!
            String entityName = entity.getCustomName​();
            if (entityName==null) entityName = entity.getName(); //notnull
            String message = Formatter.format(Lang.BEHEAD_OTHER, entityName + ChatColor.RESET, killer.getDisplayName() + ChatColor.RESET);
            int broadcastRange = plugin.getConfig().getInt("broadcastmobrange");
            broadcastRange(plugin,message,entity.getLocation(),broadcastRange);
        }
    }
    
    public static void broadcastPlayerBehead(JavaPlugin plugin, Player killer, Player player){
        if (plugin.getConfig().getBoolean("broadcast")) { //mob-on-mob broadcasts would be extremely annoying!
            String message;
            if (killer == null) {
                message = Formatter.format(Lang.BEHEAD_GENERIC, player.getDisplayName() + ChatColor.RESET);
            } else if (killer == player) {
                message = Formatter.format(Lang.BEHEAD_SELF, player.getDisplayName() + ChatColor.RESET);
            } else {
                message = Formatter.format(Lang.BEHEAD_OTHER, player.getDisplayName() + ChatColor.RESET, killer.getDisplayName() + ChatColor.RESET);
            }

            int broadcastRange = plugin.getConfig().getInt("broadcastrange");
            broadcastRange(plugin,message,player.getLocation(),broadcastRange);
        }
    }
    
    public static void broadcastBehead(JavaPlugin plugin, Player killer, Entity entity){
        if(entity instanceof Player){ broadcastPlayerBehead(plugin,killer,(Player)entity); return; }
        broadcastMobBehead(plugin, killer, entity);
    }
       
}
