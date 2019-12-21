/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api;

import com.github.crashdemons.playerheads.api.extensions.HeadExtensionManager;
import com.github.crashdemons.playerheads.SkullConverter;
import com.github.crashdemons.playerheads.SkullManager;
import com.github.crashdemons.playerheads.compatibility.Compatibility;
import com.github.crashdemons.playerheads.compatibility.CompatibilityProvider;
import com.github.crashdemons.playerheads.compatibility.adapters.BukkitOwnable;
import java.util.UUID;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.shininet.bukkit.playerheads.Config;
import org.shininet.bukkit.playerheads.Formatter;
import org.shininet.bukkit.playerheads.Lang;
import org.shininet.bukkit.playerheads.PlayerHeadsPlugin;

/**
 * Implements the API by wrapping internal methods
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class ApiProvider implements PlayerHeadsAPI {

    private final PlayerHeadsPlugin plugin;

    private PHHeadType headFromApiHead(PHHeadType h) { //TODO: remove calls to this method - now unused
        return h;
        /*if(!(h instanceof PHHeadType)){
            throw new IllegalArgumentException("The head type supplied was not created or supported by the plugin.");
        }
        return PHHeadType.get(h.getOwner());*/
    }
    
    public ApiProvider(PlayerHeadsPlugin plugin) {
        this.plugin =  plugin;
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
    public ItemStack getHeadDrop(Entity e) {
        return getHeadItem(getHeadIdentity(e), Config.defaultStackSize);
    }

    
    
    //5.1.1 API
    
    @Override
    public ItemStack getBoringPlayerheadItem(int num){
        return getHeadItem(getBoringHeadIdentity(),num);
    }
    
    @Override
    public ItemStack getHeadItem(String username, int num, boolean forceOwner){
        return getHeadItem(getHeadIdentity(username,forceOwner),num);
    }
    
    @Override
    public ItemStack getHeadItem(OfflinePlayer player, int num, boolean forceOwner){
        return getHeadItem(getHeadIdentity(player,forceOwner),num);
    }
    
    @Override
    public CompatibilityProvider getCompatibilityProvider(){
        if(!Compatibility.isProviderAvailable()) return null;
        return Compatibility.getProvider();
    }
    
    //5.2.2 API;
    
    @Override
    public HeadIdentity getHeadIdentity(Entity e){
        HeadIdentity hr = HeadExtensionManager.identifyHead(e);
        if(hr!=null) return hr;
        PHHeadType type = SkullConverter.skullTypeFromEntity(e);
        if(type==null) return null;
        if(type==PHHeadType.PLAYER){
            return getHeadIdentity(e.getName(),false);
        }else{
            return createExtendedHeadIdentity(type,"",type.getOwner());
        }
    }
    
    @Override
    public ItemStack getHeadItemFromSpawnString(String spawnName, int num, boolean forceOwner){
        HeadIdentity hr = getHeadIdentityFromSpawnString(spawnName, forceOwner);//already accounts for extensions!
        if(hr==null) throw new IllegalArgumentException("Unable to retrieve head-representation from spawn string");
        ItemStack stack = getHeadItem(hr, num);
        if(stack==null) throw new IllegalArgumentException("unable to get item from head representation");
        return stack;
    }
    
    @Override
    public HeadIdentity getHeadIdentityFromSpawnString(String spawnName, boolean forceOwner){
        HeadIdentity hr = HeadExtensionManager.getHeadBySpawnString(spawnName);
        if(hr!=null) return hr;
        PHHeadType type = PHHeadType.getBySpawnName(spawnName);
        if(spawnName.startsWith("#")) spawnName="";
        if(type==null) type=PHHeadType.PLAYER;
        if(type==PHHeadType.PLAYER) return getHeadIdentity(spawnName,forceOwner);
        return createExtendedHeadIdentity(type,"",type.getOwner());
    }
    
    @Override
    public HeadIdentity getHeadIdentity(String username, boolean forceOwner){
        if((!forceOwner) && plugin.getConfig().getBoolean("dropboringplayerheads")) return getBoringHeadIdentity();
        return createExtendedHeadIdentity(PHHeadType.PLAYER,username,null);
    }
    @Override
    public HeadIdentity getHeadIdentity(OfflinePlayer player, boolean forceOwner){
        if((!forceOwner) && plugin.getConfig().getBoolean("dropboringplayerheads")) return getBoringHeadIdentity();
        HeadIdentity hr = HeadExtensionManager.getHeadByOwner(player.getUniqueId());
        if(hr!=null) return hr;
        return createExtendedHeadIdentity(PHHeadType.PLAYER,player.getName(),player.getUniqueId());
    }
    @Override
    public HeadIdentity getBoringHeadIdentity(){
        return createExtendedHeadIdentity(PHHeadType.PLAYER,"",null);
    }
    @Override
    public HeadIdentity getHeadIdentity(ItemStack stack){
        HeadIdentity hr = HeadExtensionManager.identifyHead(stack);
        if(hr!=null) return hr;
        PHHeadType type = SkullConverter.skullTypeFromItemStack(stack);
        if(type==null) return null;
        if(!stack.hasItemMeta()) return null;
        ItemMeta meta = stack.getItemMeta();
        if(!(meta instanceof SkullMeta)) return null;
        OfflinePlayer owner = Compatibility.getProvider().getOwningPlayer((SkullMeta) meta);
        String username = Compatibility.getProvider().getOwner((SkullMeta) meta);
        hr = HeadExtensionManager.getHeadByOwner(owner.getUniqueId());
        if(hr!=null) return hr;
        return createExtendedHeadIdentity(type,username,owner.getUniqueId());
        
    }
    @Override
    public HeadIdentity getHeadIdentity(BlockState state){
        HeadIdentity hr = HeadExtensionManager.identifyHead(state);
        if(hr!=null) return hr;
        PHHeadType type = SkullConverter.skullTypeFromBlockState(state);
        if(type==null) return null;
        //if(!stack.hasItemMeta()) return null;
        //ItemMeta meta = stack.getItemMeta();
        if(!(state instanceof Skull)) return null;
        OfflinePlayer owner = Compatibility.getProvider().getOwningPlayer((Skull) state);
        String username = Compatibility.getProvider().getOwner((Skull) state);
        hr = HeadExtensionManager.getHeadByOwner(owner.getUniqueId());
        if(hr!=null) return hr;
        return createExtendedHeadIdentity(type,username,owner.getUniqueId());
    }
    @Override
    public ItemStack getHeadItem(HeadIdentity head, int num){ // TODO: heads don't receive lore from their custom-head representation.

        PHHeadType type = headFromApiHead(head.getType());
        boolean addLore = plugin.getConfig().getBoolean("addlore");
        ItemStack item;
        if(type==PHHeadType.PLAYER){
            String username = head.getOwnerName();
            if(username==null || username.isEmpty()) return SkullManager.PlayerSkull(num, addLore);
            item = SkullManager.PlayerSkull(username, num, addLore);
        }else{
            boolean usevanillaskull = plugin.getConfig().getBoolean("dropvanillaheads");
            item = SkullManager.MobSkull(type, num, usevanillaskull, addLore);
        }
        if(head instanceof HeadDisplay){
            HeadDisplay display = (HeadDisplay) head;
            ItemMeta headMeta = item.getItemMeta();
            if(headMeta instanceof SkullMeta){
                SkullManager.applyCustomHeadDetails(new BukkitOwnable((SkullMeta)headMeta), head, display);
            }
        }
        return item;
    }
    
    @Override
    public PHHeadType getCustomHeadType(){
        return PHHeadType.CUSTOM;
    }
    
    //5.3 API:
    @Override
    public HeadIdentity getHeadIdentity(EntityType et){
        PHHeadType type;
        try {
            type = PHHeadType.valueOf(et.name());
        } catch (Exception e) {
            type = null;
        }
        if(type==null) return null;
        return createExtendedHeadIdentity(type,"",type.getOwner());
    }
    
    @Override
    public HeadIdentity getHeadIdentity(PHHeadType ht){
        return createExtendedHeadIdentity(ht,"",ht.getOwner());
    }
    
    @Override
    public String getLangString(String name){
        return Lang.getString(name);
    }
    @Override
    public String formatLangString(String name, String... replacement){
        return Formatter.format(Lang.getString(name), replacement);
    }
    
    //----------------------------------------------------------------------------
    //Constructs a new headrepresenation, but allows for extensions to update it
    private HeadIdentity createExtendedHeadIdentity(final PHHeadType type, final String ownerName, final UUID ownerId){
        HeadIdentity hr = new HeadIdentity(type,ownerName,ownerId);
        return HeadExtensionManager.updateHead(hr); 
    }
}
