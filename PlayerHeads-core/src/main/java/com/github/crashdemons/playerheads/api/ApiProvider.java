/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api;

import com.github.crashdemons.playerheads.SkullConverter;
import com.github.crashdemons.playerheads.SkullManager;
import com.github.crashdemons.playerheads.TexturedSkullType;
import com.github.crashdemons.playerheads.compatibility.Compatibility;
import com.github.crashdemons.playerheads.compatibility.CompatibilityProvider;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.shininet.bukkit.playerheads.PlayerHeadsPlugin;
import org.shininet.bukkit.playerheads.PlayerHeads;

/**
 * Implements the API by wrapping internal methods
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class ApiProvider implements PlayerHeadsAPI {

    private final PlayerHeads plugin;

    public ApiProvider(PlayerHeadsPlugin plugin) {
        this.plugin = (PlayerHeads) plugin;
    }

    @Override
    public PlayerHeadsPlugin getPlugin() {
        return plugin;
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public HeadType getHeadFrom(ItemStack s) {
        return SkullConverter.skullTypeFromItemStack(s);
    }

    @Override
    public HeadType getHeadFrom(BlockState s) {
        return SkullConverter.skullTypeFromBlockState(s);
    }

    @Override
    public HeadType getHeadOf(Entity e) {
        return SkullConverter.skullTypeFromEntity(e);
    }

    @Override
    public HeadType getHeadOf(EntityType t) {
        try {
            return TexturedSkullType.valueOf(t.name());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ItemStack getHeadItem(HeadType h, int num) {
        TexturedSkullType type = headFromApiHead(h);
        if (type == null) {
            return null;
        }

        boolean addLore = plugin.configFile.getBoolean("addlore");
        boolean usevanillaskull = plugin.configFile.getBoolean("dropvanillaheads");
        if (type == TexturedSkullType.PLAYER) {
            return SkullManager.PlayerSkull(num, true);
        }
        return SkullManager.MobSkull(type, num, usevanillaskull, addLore);
    }

    @Override
    public ItemStack getHeadDrop(Entity e) {
        TexturedSkullType type = SkullConverter.skullTypeFromEntity(e);
        if (type == null) {
            return null;
        }
        boolean addLore = plugin.configFile.getBoolean("addlore");
        ItemStack drop;
        if (e instanceof Player) {
            Player player = (Player) e;
            String skullOwner;
            if (plugin.configFile.getBoolean("dropboringplayerheads")) {
                skullOwner = "";
            } else {
                skullOwner = player.getName();
            }
            if (skullOwner == null || skullOwner.isEmpty()) {
                drop = SkullManager.PlayerSkull(addLore);
            } else {
                drop = SkullManager.PlayerSkull(skullOwner, addLore);
            }
        } else {
            boolean usevanillaskull = plugin.configFile.getBoolean("dropvanillaheads");
            drop = SkullManager.MobSkull(type, usevanillaskull, addLore);
        }
        return drop;
    }

    private TexturedSkullType headFromApiHead(HeadType h) {
        return TexturedSkullType.get(h.getOwner());
    }
    
    
    //5.1.1 API
    
    @Override
    public ItemStack getBoringPlayerheadItem(int num){
        boolean addLore = plugin.configFile.getBoolean("addlore");
        return SkullManager.PlayerSkull(num,addLore);
    }
    
    @Override
    public ItemStack getHeadItem(String username, int num, boolean forceOwner){
        if(!forceOwner && plugin.configFile.getBoolean("dropboringplayerheads")){
            return getBoringPlayerheadItem(num);
        }else{
            boolean addLore = plugin.configFile.getBoolean("addlore");
            return SkullManager.PlayerSkull(username, num, addLore);
        }
    }
    
    @Override
    public ItemStack getHeadItem(OfflinePlayer player, int num, boolean forceOwner){
        String name = player.getName();
        if(name==null) return null;
        return getHeadItem(name,num,forceOwner);
    }
    
    @Override
    public CompatibilityProvider getCompatibilityProvider(){
        if(!Compatibility.isProviderAvailable()) return null;
        return Compatibility.getProvider();
    }
    
    //5.2.2 API;
    @Override
    public HeadRepresentation getHeadRepresentation(String username, boolean forceOwner){
        if((!forceOwner) && plugin.configFile.getBoolean("dropboringplayerheads")) return getHeadRepresentationFromBoringPlayerhead();
        return new InternalHeadRepresentation(TexturedSkullType.PLAYER,username);
    }
    @Override
    public HeadRepresentation getHeadRepresentation(OfflinePlayer player, boolean forceOwner){
        if((!forceOwner) && plugin.configFile.getBoolean("dropboringplayerheads")) return getHeadRepresentationFromBoringPlayerhead();
        return new InternalHeadRepresentation(TexturedSkullType.PLAYER,player.getName(),player.getUniqueId());
    }
    @Override
    public HeadRepresentation getHeadRepresentationFromBoringPlayerhead(){
        return new InternalHeadRepresentation(TexturedSkullType.PLAYER,null,null);
    }
    @Override
    public HeadRepresentation getHeadRepresentation(ItemStack stack){
        TexturedSkullType type = SkullConverter.skullTypeFromItemStack(stack);
        if(type==null) return null;
        if(!stack.hasItemMeta()) return null;
        ItemMeta meta = stack.getItemMeta();
        if(!(meta instanceof SkullMeta)) return null;
        OfflinePlayer owner = Compatibility.getProvider().getOwningPlayer((SkullMeta) meta);
        String username = Compatibility.getProvider().getOwner((SkullMeta) meta);
        return new InternalHeadRepresentation(type,username,owner.getUniqueId());
        
    }
    @Override
    public HeadRepresentation getHeadRepresentation(BlockState state){
        TexturedSkullType type = SkullConverter.skullTypeFromBlockState(state);
        if(type==null) return null;
        //if(!stack.hasItemMeta()) return null;
        //ItemMeta meta = stack.getItemMeta();
        if(!(state instanceof Skull)) return null;
        OfflinePlayer owner = Compatibility.getProvider().getOwningPlayer((Skull) state);
        String username = Compatibility.getProvider().getOwner((Skull) state);
        return new InternalHeadRepresentation(type,username,owner.getUniqueId());
    }
    @Override
    public ItemStack getHeadItem(HeadRepresentation head, int num){
        TexturedSkullType type = headFromApiHead(head.getType());
        boolean addLore = plugin.configFile.getBoolean("addlore");
        if(type==TexturedSkullType.PLAYER){
            String username = head.getOwnerName();
            if(username==null) return SkullManager.PlayerSkull(num, addLore);
            return SkullManager.PlayerSkull(username, num, addLore);
        }else{
            boolean usevanillaskull = plugin.configFile.getBoolean("dropvanillaheads");
            return SkullManager.MobSkull(type, num, usevanillaskull, addLore);
        }
    }
    
}
