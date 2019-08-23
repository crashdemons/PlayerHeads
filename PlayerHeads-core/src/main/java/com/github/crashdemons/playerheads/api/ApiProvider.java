/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api;

import com.github.crashdemons.playerheads.api.extensions.ExtensionManager;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.shininet.bukkit.playerheads.Config;
import org.shininet.bukkit.playerheads.PlayerHeadsPlugin;
import org.shininet.bukkit.playerheads.PlayerHeads;

/**
 * Implements the API by wrapping internal methods
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class ApiProvider implements PlayerHeadsAPI {

    private final PlayerHeads plugin;

    private TexturedSkullType headFromApiHead(HeadType h) {
        if(!(h instanceof TexturedSkullType)){
            throw new IllegalArgumentException("The head type supplied was not created or supported by the plugin.");
        }
        return TexturedSkullType.get(h.getOwner());
    }
    
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
        return getHeadItem(new HeadRepresentation(headFromApiHead(h),""),num);
    }

    @Override
    public ItemStack getHeadDrop(Entity e) {
        return getHeadItem(getHeadRepresentation(e), Config.defaultStackSize);
    }

    
    
    //5.1.1 API
    
    @Override
    public ItemStack getBoringPlayerheadItem(int num){
        return getHeadItem(getHeadRepresentationFromBoringPlayerhead(),num);
    }
    
    @Override
    public ItemStack getHeadItem(String username, int num, boolean forceOwner){
        return getHeadItem(getHeadRepresentation(username,forceOwner),num);
    }
    
    @Override
    public ItemStack getHeadItem(OfflinePlayer player, int num, boolean forceOwner){
        return getHeadItem(getHeadRepresentation(player,forceOwner),num);
    }
    
    @Override
    public CompatibilityProvider getCompatibilityProvider(){
        if(!Compatibility.isProviderAvailable()) return null;
        return Compatibility.getProvider();
    }
    
    //5.2.2 API;
    
    @Override
    public HeadRepresentation getHeadRepresentation(Entity e){
        TexturedSkullType type = SkullConverter.skullTypeFromEntity(e);
        if(type==null) return null;
        if(type==TexturedSkullType.PLAYER){
            return getHeadRepresentation(e.getName(),false);
        }else{
            return new HeadRepresentation(type,"",type.getOwner());
        }
    }
    
    @Override
    public ItemStack getHeadItemFromSpawnString(String spawnName, int num, boolean forceOwner){
        HeadRepresentation hr = getHeadRepresentationFromSpawnString(spawnName, forceOwner);
        if(hr==null) throw new IllegalArgumentException("Unable to retrieve head-representation from spawn string");
        ItemStack stack = getHeadItem(hr, num);
        if(stack==null) throw new IllegalArgumentException("unable to get item from head representation");
        return stack;
    }
    
    @Override
    public HeadRepresentation getHeadRepresentationFromSpawnString(String spawnName, boolean forceOwner){
        HeadRepresentation hr = ExtensionManager.getHeadBySpawnString(spawnName);
        if(hr!=null) return hr;
        TexturedSkullType type = TexturedSkullType.getBySpawnName(spawnName);
        if(spawnName.startsWith("#")) spawnName="";
        if(type==null) type=TexturedSkullType.PLAYER;
        if(type==TexturedSkullType.PLAYER) return getHeadRepresentation(spawnName,forceOwner);
        return new HeadRepresentation(type,"",type.getOwner());
    }
    
    @Override
    public HeadRepresentation getHeadRepresentation(String username, boolean forceOwner){
        if((!forceOwner) && plugin.configFile.getBoolean("dropboringplayerheads")) return getHeadRepresentationFromBoringPlayerhead();
        return new HeadRepresentation(TexturedSkullType.PLAYER,username);
    }
    @Override
    public HeadRepresentation getHeadRepresentation(OfflinePlayer player, boolean forceOwner){
        if((!forceOwner) && plugin.configFile.getBoolean("dropboringplayerheads")) return getHeadRepresentationFromBoringPlayerhead();
        HeadRepresentation hr = ExtensionManager.getHeadByOwner(player.getUniqueId());
        if(hr!=null) return hr;
        return new HeadRepresentation(TexturedSkullType.PLAYER,player.getName(),player.getUniqueId());
    }
    @Override
    public HeadRepresentation getHeadRepresentationFromBoringPlayerhead(){
        return new HeadRepresentation(TexturedSkullType.PLAYER,"",null);
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
        HeadRepresentation hr = ExtensionManager.getHeadByOwner(owner.getUniqueId());
        if(hr!=null) return hr;
        return new HeadRepresentation(type,username,owner.getUniqueId());
        
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
        HeadRepresentation hr = ExtensionManager.getHeadByOwner(owner.getUniqueId());
        if(hr!=null) return hr;
        return new HeadRepresentation(type,username,owner.getUniqueId());
    }
    @Override
    public ItemStack getHeadItem(HeadRepresentation head, int num){
        TexturedSkullType type = headFromApiHead(head.getType());
        boolean addLore = plugin.configFile.getBoolean("addlore");
        if(type==TexturedSkullType.PLAYER){
            String username = head.getOwnerName();
            if(username==null || username.isEmpty()) return SkullManager.PlayerSkull(num, addLore);
            return SkullManager.PlayerSkull(username, num, addLore);
        }else{
            boolean usevanillaskull = plugin.configFile.getBoolean("dropvanillaheads");
            return SkullManager.MobSkull(type, num, usevanillaskull, addLore);
        }
    }
    
    @Override
    public HeadType getCustomHeadType(){
        return TexturedSkullType.CUSTOM;
    }
    
}
