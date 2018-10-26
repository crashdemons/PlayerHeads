/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.bukkit_1_8;

import com.github.crashdemons.playerheads.compatibility.Version;
import com.github.crashdemons.playerheads.compatibility.RuntimeReferences;
import com.github.crashdemons.playerheads.compatibility.SkullDetails;
import com.github.crashdemons.playerheads.compatibility.SkullType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
/*
public class SkullDetails_18_19 implements SkullDetails {
    public final Material materialItem;
    public final Material materialBlock;
    public final short datavalue;
    private final SkullType skullType;
    
    public SkullDetails_18_19(SkullType skullType){
        if(skullType==null) skullType=SkullType.PLAYER;
        this.skullType=skullType;
        materialBlock=RuntimeReferences.getMaterialByName("SKULL");
        materialItem=RuntimeReferences.getMaterialByName("SKULL_ITEM");
        
        if(skullType==SkullType.DRAGON && CompatibilityChecker.checkVersionUnder(1, 9)){
            datavalue=(short) SkullType.PLAYER.ordinal();
        }else{
            datavalue=(short) skullType.ordinal();
        }
    }
    @Override public boolean isVariant(){ return materialItem==Material.SKULL_ITEM; }
    @Override public boolean isSkinnable(){ return materialItem==Material.SKULL_ITEM && skullType==SkullType.PLAYER; }
    @Override public ItemStack createItemStack(int quantity){ return new ItemStack(materialItem,quantity,datavalue); }
    @Override public Material getItemMaterial(){ return materialItem; }
    @Override public Material getFloorMaterial(){ return materialBlock; }
    @Override public Material getWallMaterial(){ return materialBlock; }
}
*/