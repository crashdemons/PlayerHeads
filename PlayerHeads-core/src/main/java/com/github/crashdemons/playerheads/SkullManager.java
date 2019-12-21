
package com.github.crashdemons.playerheads;

import com.github.crashdemons.playerheads.api.HeadIdentity;
import com.github.crashdemons.playerheads.compatibility.Compatibility;
import com.github.crashdemons.playerheads.compatibility.BackwardsCompatibleSkullType;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import org.shininet.bukkit.playerheads.Config;
import org.shininet.bukkit.playerheads.Lang;
import com.github.crashdemons.playerheads.api.HeadDisplay;
import com.github.crashdemons.playerheads.api.extensions.CustomHead;
import com.github.crashdemons.playerheads.compatibility.adapters.BukkitOwnable;
import java.util.Arrays;

/**
 * Defines an abstract class of methods for creating, updating, and applying information to heads managed by the plugin.
 * @author crash
 */
public final class SkullManager {
    
    private SkullManager(){}
    
    //untested / unused
    public static ItemStack CustomSkull(CustomHead customHead, int quantity, boolean useVanillaHeads){
        return CustomSkull(customHead, customHead, quantity, useVanillaHeads);
    }
    //untested / unused
    public static ItemStack CustomSkull(HeadIdentity identity, HeadDisplay display, int quantity, boolean useVanillaHeads){
        TexturedSkullType type = ((TexturedSkullType) identity.getType());
        if(type==null) return null;
        BackwardsCompatibleSkullType mat = type.getCompatibleMaterial();
        
        if(type.hasDedicatedItem()){
            if(useVanillaHeads)
                return mat.getDetails().createItemStack(quantity);//new ItemStack(mat,quantity);
            else mat=BackwardsCompatibleSkullType.PLAYER;
        }
        
        ItemStack stack = mat.getDetails().createItemStack(quantity);//new ItemStack(mat,quantity);
        SkullMeta headMeta = (SkullMeta) stack.getItemMeta();
        applyCustomHeadDetails(new BukkitOwnable(headMeta), identity, display);
        stack.setItemMeta(headMeta);
        return stack;
    }
    
    
    
    
    public static void applyCustomHeadDetails(BukkitOwnable bukkitHead, CustomHead customHead){
        applyCustomHeadDetails(bukkitHead, customHead, customHead);
    }
    public static void applyCustomHeadDetails(BukkitOwnable bukkitHead, HeadIdentity identity, HeadDisplay display){
        if(bukkitHead.getBaseObject() instanceof ItemMeta){
            ItemMeta meta = (ItemMeta) bukkitHead.getBaseObject();
            meta.setDisplayName(display.getDisplayName());
            List<String> lore = display.getLore();
            if(lore!=null){
                meta.setLore(new ArrayList<>(lore));
            }
        }
        UUID id = identity.getOwnerId();
        OfflinePlayer op = Bukkit.getOfflinePlayer(id);
        bukkitHead.setOwner(identity.getOwnerName());
        bukkitHead.setOwningPlayer(op);
        bukkitHead.setProfile(id, display.getTexture());
    }

    /**
     * Gets full PlayerHeads Lore text (including the PlayerHeads plugin name).
     * @param extra Extra lore text to display under the "PlayerHeads" line.
     */
    private static List<String> getExtendedLore(String extra){
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        if(!Lang.LORE_PLUGIN_NAME.isEmpty()) lore.add(ChatColor.BLUE+""+ChatColor.ITALIC+Lang.LORE_PLUGIN_NAME);
        if(!extra.isEmpty()) lore.add(extra);
        return lore;
    }
    
    
    
    /**
     * Creates a stack of heads for the specified Mob's SkullType.
     * 
     * The quantity of heads will be defined by Config.defaultStackSize (usually 1)
     * 
     * @param type The TexturedSkullType to create heads of.
     * @param useVanillaHeads Whether to permit vanilla head-items to be used in place of custom playerheads for supported mobs.
     * @param addLore controls whether any lore text should be added to the head (is currently applied only to custom heads).
     * @return The ItemStack of heads desired.
     * @see org.shininet.bukkit.playerheads.Config#defaultStackSize
     */
    public static ItemStack MobSkull(TexturedSkullType type, boolean useVanillaHeads, boolean addLore){
        return MobSkull(type,Config.defaultStackSize, useVanillaHeads, addLore);
    }
    
    /**
     * Creates a stack of heads for the specified Mob's SkullType
     * @param type The TexturedSkullType to create heads of.
     * @param quantity the number of heads to create in the stack
     * @param useVanillaHeads Whether to permit vanilla head-items to be used in place of custom playerheads for supported mobs.
     * @param addLore controls whether any lore text should be added to the head (is currently applied only to custom heads).
     * @return The ItemStack of heads desired.
     */
    public static ItemStack MobSkull(TexturedSkullType type,int quantity,boolean useVanillaHeads, boolean addLore){
        BackwardsCompatibleSkullType mat = type.getCompatibleMaterial();
        
        if(type.hasDedicatedItem()){
            if(useVanillaHeads)
                return mat.getDetails().createItemStack(quantity);//new ItemStack(mat,quantity);
            else mat=BackwardsCompatibleSkullType.PLAYER;
        }
        
        ItemStack stack = mat.getDetails().createItemStack(quantity);//new ItemStack(mat,quantity);
        SkullMeta headMeta = (SkullMeta) stack.getItemMeta();
        
        String displayName = ChatColor.RESET + "" + ChatColor.YELLOW + type.getDisplayName();
        String spawnString = type.getSpawnName();
        String texture = type.getTexture();
        UUID owner = type.getOwner();
        List<String> lore = getExtendedLore(ChatColor.GREEN+Lang.LORE_HEAD_MOB);
        
        
        CustomHead customHead = new CustomHead(owner, null, spawnString, displayName, texture, lore);
        applyCustomHeadDetails(new BukkitOwnable(headMeta), customHead);

        stack.setItemMeta(headMeta);
        return stack;
    }
    private static ItemStack PlayerSkull(OfflinePlayer owner, boolean addLore){
        return PlayerSkull(owner,Config.defaultStackSize, addLore);
    }
    private static ItemStack PlayerSkull(OfflinePlayer owner, int quantity, boolean addLore){
        ItemStack stack = BackwardsCompatibleSkullType.PLAYER.getDetails().createItemStack(quantity);//new ItemStack(Material.PLAYER_HEAD,quantity);
        SkullMeta headMeta = (SkullMeta) stack.getItemMeta();
        
        String name=null;
        if(owner!=null){
            name = owner.getName();
        }
        if(name==null) name="Player";//only used for display purposes.
        String displayName = ChatColor.RESET + "" + ChatColor.YELLOW + TexturedSkullType.getDisplayName(name);
        String spawnString = name.toLowerCase();
        String texture = null;
        List<String> lore = getExtendedLore(ChatColor.RED+Lang.LORE_HEAD_PLAYER);
        
        CustomHead customHead = new CustomHead(owner.getUniqueId(), owner.getName(), spawnString, displayName, texture, lore);
        
        applyCustomHeadDetails(new BukkitOwnable(headMeta), customHead);
        
        stack.setItemMeta(headMeta);
        return stack;
    }
    
    /**
     * Creates a stack of playerheads for no owner
     *  
     * @param addLore controls whether any lore text should be added to the head.
     * @return The ItemStack of heads desired.
     */
    public static ItemStack PlayerSkull(boolean addLore){
        return PlayerSkull(Config.defaultStackSize,addLore);
    }
    
    /**
     * Creates a stack of playerheads for no owner
     *  
     * @param quantity The number of heads to create in the stack.
     * @param addLore controls whether any lore text should be added to the head.
     * @return The ItemStack of heads desired.
     */
    public static ItemStack PlayerSkull(int quantity, boolean addLore){
        return PlayerSkull((OfflinePlayer) null,quantity, addLore);
    }
    
    /**
     * Creates a stack of playerheads for the given username.
     * 
     * The username given is translated to an OfflinePlayer by bukkit at the time of creation.
     * 
     * Note: if the owner name is null or can't be translated to a player internally, a generic playerhead will be created with the name "Unknown".
     * 
     * The quantity of heads will be defined by Config.defaultStackSize (usually 1)
     * @param owner The username to create a head for.
     * @param addLore controls whether any lore text should be added to the head.
     * @return The ItemStack of heads desired.
     * @see org.shininet.bukkit.playerheads.Config#defaultStackSize
     */
    public static ItemStack PlayerSkull(String owner, boolean addLore){
        return PlayerSkull(owner,Config.defaultStackSize, addLore);
    }
    /**
     * Creates a stack of playerheads for the given username.
     * 
     * The username given is translated to an OfflinePlayer by bukkit at the time of creation. NOTE: null or empty usernames will be rejected with an IllegalArgumentException
     * 
     * @param owner The username to create a head for.
     * @param quantity The number of heads to create in the stack.
     * @param addLore controls whether any lore text should be added to the head.
     * @return The ItemStack of heads desired.
     * @throws IllegalArgumentException passed a null or empty username.
     */
    public static ItemStack PlayerSkull(String owner, int quantity, boolean addLore){
        if(owner==null || owner.isEmpty()) throw new IllegalArgumentException("Creating a playerhead with a null or empty username is not possible with this method.");
        OfflinePlayer op = Compatibility.getProvider().getOfflinePlayerByName(owner);
        return PlayerSkull(op,quantity, addLore);
    }
    
    /**
     * Updates the blockstate of a head.
     * 
     * Originally this method also updated legacy username-based skulls to the correct owner - currently it only updates the blockstate.
     * Since these skulls are upgraded and textured-skulls have embedded textures, this method may not be necessary and may be removed in the future.
     * 
     * @param skullState the blockstate to update.
     */
    public static void updatePlayerSkullState(BlockState skullState){
        //for a skull belonging to a player drop, this shouldn't really be necessary to reset the owner.
        //and for textured mobheads, the texture is embedded, so shouldn't need updating...
        /*
        OfflinePlayer op = skullState.getOwningPlayer();
        String owner=null;
        if(op!=null) owner=op.getName();
        if(owner==null) owner=skullState.getOwner();//this is deprecated, but the above method does NOT get the name tag from the NBT.
        if(owner==null) return;

        skullState.setOwningPlayer(Bukkit.getOfflinePlayer(skullType.getOwner()));
        */
        skullState.update();
    }
}
