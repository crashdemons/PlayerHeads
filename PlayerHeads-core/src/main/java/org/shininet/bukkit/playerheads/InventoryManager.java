/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package org.shininet.bukkit.playerheads;

import com.github.crashdemons.playerheads.SkullManager;
import com.github.crashdemons.playerheads.TexturedSkullType;
import com.github.crashdemons.playerheads.api.HeadRepresentation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Defines a collection of utility methods for the plugin inventory management, item management
 * @author meiskam
 */
public final class InventoryManager {
    private InventoryManager(){}
    
    private static boolean addItem(Player player, ItemStack stack){
        PlayerInventory inv = player.getInventory();
        int firstEmpty = inv.firstEmpty();
        if (firstEmpty == -1) {
            return false;
        } else {
            inv.setItem(firstEmpty, stack);
            return true;
        }
    }
    
    private static ItemStack getHead(Player player, String spawnName, int quantity){
        ItemStack stack = PlayerHeads.instance.api.getHeadItemFromSpawnString(spawnName, quantity, false);
        if(stack==null) throw new IllegalArgumentException("unable to get item from head representation");
        return stack;
    }
    
    public static boolean addHead(Player player, String spawnName, int quantity){
        HeadRepresentation hr = PlayerHeads.instance.api.getHeadRepresentationFromSpawnString(spawnName, false);
        if(hr==null) throw new IllegalArgumentException("Unable to retrieve head-representation from spawn string");
        ItemStack stack = PlayerHeads.instance.api.getHeadItem(hr, quantity);
        if(stack==null) throw new IllegalArgumentException("unable to get item from head representation");
        return addItem(player, stack);
    }
    
    /**
     * Adds a head-item to a player's inventory.
     * 
     * The quantity of heads added to the inventory is controlled by Config.defaultStackSize (usually 1).
     * @param player the player receiving the head.
     * @param skullOwner the owner username of the head-item, or the "spawn" string for a supported skull.
     * @param usevanillaskulls whether vanilla mobheads are permitted (if relevant to the owner parameter).
     * @param addLore controls whether lore text can be added to the head by the plugin
     * @return true: the head was added successfully. false: there was no empty inventory slot to add the item.
     * @see Config#defaultStackSize
     */
    @SuppressWarnings("unused")
    public static boolean addHead(Player player, String skullOwner, boolean usevanillaskulls, boolean addLore) {
        return addHead(player, skullOwner, Config.defaultStackSize, usevanillaskulls, addLore);
    }

    /**
     * Adds a head-item to a player's inventory.
     * @param player the player receiving the head.
     * @param skullOwner the owner username of the head-item, or the "spawn" string for a supported skull.
     * @param quantity the number of this item to add
     * @param usevanillaskulls whether vanilla mobheads are permitted (if relevant to the owner parameter).
     * @param addLore controls whether lore text can be added to the head by the plugin
     * @return true: the head was added successfully. false: there was no empty inventory slot to add the item.
     */
    public static boolean addHead(Player player, String skullOwner, int quantity, boolean usevanillaskulls,boolean addLore) {
        return addItem(player, SkullManager.spawnSkull(skullOwner, quantity, usevanillaskulls, addLore));
    }
}
