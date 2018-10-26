/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public interface CompatibilityProvider {
    public String getVersion();
    public OfflinePlayer getOwningPlayer(SkullMeta skullItemMeta);
    public OfflinePlayer getOwningPlayer(Skull skullBlockState);
    public String getOwner(SkullMeta skullItemMeta);
    public String getOwner(Skull skullBlockState);
    public boolean setOwningPlayer(SkullMeta skullItemMeta, OfflinePlayer op);
    public void    setOwningPlayer(Skull skullBlockState, OfflinePlayer op);
    public boolean setOwner(SkullMeta skullItemMeta, String owner);
    public boolean setOwner(Skull skullBlockState, String owner);
    public ItemStack getItemInMainHand(Player p);
    public void setItemInMainHand(Player p,ItemStack s);
    public SkullDetails getSkullDetails(SkullType type);
}
