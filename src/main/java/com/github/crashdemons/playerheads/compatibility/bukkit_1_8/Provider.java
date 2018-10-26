/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.bukkit_1_8;

import com.github.crashdemons.playerheads.ProfileUtils;
import com.github.crashdemons.playerheads.compatibility.CompatibilityProvider;
import com.github.crashdemons.playerheads.compatibility.SkullDetails;
import com.github.crashdemons.playerheads.compatibility.SkullType;
import com.github.crashdemons.playerheads.compatibility.bukkit_1_8.SkullDetails_18_19;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class Provider implements CompatibilityProvider {
    @Override public String getVersion(){ return "1.13"; }
    @Override public OfflinePlayer getOwningPlayer(SkullMeta skullItemMeta){ return ProfileUtils.getProfilePlayer(skullItemMeta); }
    @Override public OfflinePlayer getOwningPlayer(Skull skullBlockState){ return ProfileUtils.getProfilePlayer(skullBlockState); }
    @Override public String getOwner(SkullMeta skullItemMeta){ return skullItemMeta.getOwner(); }
    @Override public String getOwner(Skull skullBlockState){ return skullBlockState.getOwner(); }
    @Override public boolean setOwningPlayer(SkullMeta skullItemMeta, OfflinePlayer op){ return skullItemMeta.setOwner(op.getName()); }
    @Override public void    setOwningPlayer(Skull skullBlockState, OfflinePlayer op){ skullBlockState.setOwner(op.getName()); }
    @Override public boolean setOwner(SkullMeta skullItemMeta, String owner){ return skullItemMeta.setOwner(owner); }
    @Override public boolean setOwner(Skull skullBlockState, String owner){ return skullBlockState.setOwner(owner); }
    @Override public ItemStack getItemInMainHand(Player p){ return p.getEquipment().getItemInHand(); }
    @Override public void setItemInMainHand(Player p,ItemStack s){ p.getEquipment().setItemInHand(s); }
    public SkullDetails getSkullDetails(SkullType type){ return new SkullDetails_18_19(type); }
}
