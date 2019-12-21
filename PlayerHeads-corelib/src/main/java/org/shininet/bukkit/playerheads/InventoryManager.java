/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package org.shininet.bukkit.playerheads;

import com.github.crashdemons.playerheads.api.HeadIdentity;
import com.github.crashdemons.playerheads.api.PlayerHeads;
import com.github.crashdemons.playerheads.api.PlayerHeadsAPI;
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

    public static boolean addHead(Player player, String spawnName, int quantity){
        PlayerHeadsAPI api = PlayerHeads.getApiInstance();
        HeadIdentity hr = api.getHeadIdentityFromSpawnString(spawnName, false);
        if(hr==null) throw new IllegalArgumentException("Unable to retrieve head-representation from spawn string");
        ItemStack stack = api.getHeadItem(hr, quantity);
        if(stack==null) throw new IllegalArgumentException("unable to get item from head representation");
        return addItem(player, stack);
    }
}
