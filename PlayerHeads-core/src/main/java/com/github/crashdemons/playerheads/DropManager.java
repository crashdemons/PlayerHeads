/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.shininet.bukkit.playerheads.PlayerHeads;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class DropManager {
    private final static long TICKS_PER_SECOND = 20;
    private final static long MS_PER_TICK = 1000/TICKS_PER_SECOND; //50
    private DropManager(){}
    
    public static void dropItemNow(Location location, ItemStack drop, boolean naturally){
        if(naturally) location.getWorld().dropItemNaturally(location, drop);
        else location.getWorld().dropItem(location, drop);
    }
    public static void dropItems(PlayerHeads plugin,  List<ItemStack> drops, final Location location, final boolean naturally,  final boolean delayDrop, final long tickDelay){
        for(final ItemStack drop : drops){
            if(delayDrop){
                plugin.scheduleSync(new Runnable(){
                    @Override
                    public void run(){
                        //System.out.println(" delayed head drop running");
                        dropItemNow(location,drop,naturally);
                    }
                },tickDelay);
                //System.out.println("scheduled head drop for "+ticks+" ticks");
            }else{
                dropItemNow(location,drop,naturally);
            }
        }
    }
    public static void requestDrops(PlayerHeads plugin, List<ItemStack> drops, boolean isWitherDrop, EntityDeathEvent event){
        if(drops.isEmpty()) return;
        if(isWitherDrop && plugin.configFile.getBoolean("delaywitherdrop")){
            int delay = plugin.configFile.getInt("delaywitherdropms");
            long ticks =  delay / MS_PER_TICK;
            final Location location = event.getEntity().getLocation();
            dropItems(plugin, drops, location, true, true, ticks);
        }else if (plugin.configFile.getBoolean("antideathchest") || event==null) {
            Location location = event.getEntity().getLocation();
            dropItems(plugin, drops, location, true, false, 0);
        } else {
            event.getDrops().addAll(drops);
        }
    }
    public static void requestDrops(PlayerHeads plugin, List<ItemStack> drops){
        requestDrops(plugin,drops,false,null);
    }
}
