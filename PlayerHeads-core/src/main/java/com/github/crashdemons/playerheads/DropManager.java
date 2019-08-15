/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads;

import com.github.crashdemons.playerheads.compatibility.Compatibility;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.shininet.bukkit.playerheads.Config;
import org.shininet.bukkit.playerheads.PlayerHeads;
import org.shininet.bukkit.playerheads.events.BlockDropHeadEvent;

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
    
    
    public static ItemStack createConvertedMobhead(PlayerHeads plugin, TexturedSkullType skullType, boolean isSourceSkinnable, boolean addLore, int quantity) {
        boolean usevanillaskull = plugin.configFile.getBoolean("dropvanillaheads");
        boolean convertvanillahead = plugin.configFile.getBoolean("convertvanillaheads");

        //if the head is a skinned playerhead and usevanillaskull is set, then breaking it would convert it to a vanilla head
        //if the head is a vanilla skull/head and usevanillaskull is unset, then breaking would convert it to a skinned head
        boolean conversionCanHappen = SkullConverter.canConversionHappen(usevanillaskull, isSourceSkinnable);
        if (conversionCanHappen && !convertvanillahead) {
            usevanillaskull = !usevanillaskull;//change the drop to the state that avoids converting it.
        }
        return SkullManager.MobSkull(skullType, quantity, usevanillaskull, addLore);
    }
    
    //drop a head based on a block being broken in some fashion
    //NOTE: the blockbreak handler expects this to unconditionally drop the item unless the new event is cancelled.
    public static BlockDropResult blockDrop(PlayerHeads plugin, BlockEvent event, Block block, BlockState state) {
        TexturedSkullType skullType = SkullConverter.skullTypeFromBlockState(state);
        Location location = block.getLocation();
        ItemStack item = null;
        boolean addLore = plugin.configFile.getBoolean("addlore");
        switch (skullType) {
            case PLAYER:
                Skull skull = (Skull) block.getState();
                String owner = Compatibility.getProvider().getOwner(skull);//SkullConverter.getSkullOwner(skull);
                if (owner == null) {
                    return BlockDropResult.FAILED_CUSTOM_HEAD;//you broke an unsupported custom-textured head.
                }
                item = SkullManager.PlayerSkull(owner, addLore);
                break;
            default:
                boolean blockIsSkinnable = Compatibility.getProvider().isPlayerhead(block.getState());
                item = DropManager.createConvertedMobhead(plugin,skullType, blockIsSkinnable, addLore, Config.defaultStackSize);
                break;
        }
        block.setType(Material.AIR);
        BlockDropHeadEvent eventDropHead = new BlockDropHeadEvent(block, item);
        plugin.getServer().getPluginManager().callEvent(eventDropHead);
        if (eventDropHead.isCancelled()) {
            return BlockDropResult.FAILED_EVENT_CANCELLED;
        }
        DropManager.requestDrops(plugin, eventDropHead.getDrops());
        return BlockDropResult.SUCCESS;
    }
}
