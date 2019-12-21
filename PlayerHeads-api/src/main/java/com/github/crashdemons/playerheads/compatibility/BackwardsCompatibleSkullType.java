/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility;

import com.github.crashdemons.playerheads.api.PlayerHeads;
import com.github.crashdemons.playerheads.api.PlayerHeadsAPI;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;

/**
 * An enumeration of vanilla skull/head materials (items and blocks) that can
 * possibly be supported.
 * <p>
 * This abstracts both new materials and old materials+datavalues (skull
 * variants) into matching categories by enum along with their
 * implementation-specific details.
 * <p>
 * Entries from newer versions may appear here, but are backed by playerhead
 * materials if they are not supported - which can be checked with
 * isSupported().
 * Generally this corresponds with an entry for each entry in SkullType
 *
 * @see #isSupported()
 * @see SkullType
 * @author crashdemons (crashenator at gmail.com)
 */
public enum BackwardsCompatibleSkullType {//should maintain compatibility with EntityType
    SKELETON(SkullType.SKELETON),
    WITHER_SKELETON(SkullType.WITHER_SKELETON),
    ZOMBIE(SkullType.ZOMBIE),
    PLAYER(SkullType.PLAYER),
    CREEPER(SkullType.CREEPER),
    /**
     * 1.9 dragon head
     */
    ENDER_DRAGON(SkullType.DRAGON);

    private final SkullType skullType;
    private SkullDetails cachedDetails = null;

    BackwardsCompatibleSkullType(SkullType type) {
        skullType = type;
    }

    /**
     * Gets a class describing implementation-specific details about a vanilla
     * skull
     *
     * @return the object with details about the skull
     */
    public SkullDetails getDetails() {
        if (cachedDetails == null) {
            cachedDetails = PlayerHeads.getApiInstance().getCompatibilityProvider().getSkullDetails(skullType);
        }
        return cachedDetails;
    }

    /**
     * Whether this skull material is supported as a vanilla item/block type.
     * <p>
     * If this returns false, the skull is only supported as a playerhead (the
     * PLAYER type will return true however).
     *
     * @return whether the skull is supported as a vanilla type
     * @see SkullDetails#isBackedByPlayerhead()
     */
    public boolean isSupported() {
        return this == PLAYER || !getDetails().isBackedByPlayerhead();
    }

    
    public static BackwardsCompatibleSkullType getByName(String name){
        try{
            return BackwardsCompatibleSkullType.valueOf(name);
        }catch(IllegalArgumentException e){
            return null;
        }
    }
    
    
    /**
     * Finds a skull material enum entry associated with the vanilla skull-type.
     *
     * @param type the vanilla skull type to reference
     * @return the skull material enum entry
     */
    public static BackwardsCompatibleSkullType get(SkullType type) {
        if (type == null) {
            return null;
        }
        if (type == SkullType.DRAGON) {
            return ENDER_DRAGON;//item to entity correlation here
        }
        if (type == SkullType.WITHER_SKELETON) {
            return WITHER_SKELETON;//item to entity correlation here
        }
        return getByName(type.name());//otherwise, our SkullType has a 1:1 mapping with CompatibleSkullMaterial
    }

    
    
    
    
    /**
     * Finds a skull material enum entry that best fits with the provided
     * ItemStack.
     * <p>
     * Heads that are not supported in the current server version will return
     * PLAYER using this method, instead of their original enum entry.
     *
     * @param stack the itemstack to check
     * @return the skull material enum entry
     * @see CompatibilityProvider#getSkullType(org.bukkit.inventory.ItemStack)
     */
    public static BackwardsCompatibleSkullType get(ItemStack stack) {
        return get(PlayerHeads.getApiInstance().getCompatibilityProvider().getSkullType(stack));
    }

    /**
     * Finds a skull material enum entry that best fits with the provided
     * BlockState.
     * <p>
     * Heads that are not supported in the current server version will return
     * PLAYER using this method, instead of their original enum entry.
     *
     * @param state the blockstate to check
     * @return the skull material enum entry
     * @see CompatibilityProvider#getSkullType(org.bukkit.block.BlockState)
     */
    public static BackwardsCompatibleSkullType get(BlockState state) {
        return get(PlayerHeads.getApiInstance().getCompatibilityProvider().getSkullType(state));
    }

}
