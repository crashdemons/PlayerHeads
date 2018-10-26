/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public enum CompatibleSkullMaterial {//should maintain compatibility with EntityType
    SKELETON(SkullType_19.SKELETON,SkullType_113.SKELETON),
    WITHER_SKELETON(SkullType_19.WITHER,SkullType_113.WITHER_SKELETON),
    ZOMBIE(SkullType_19.ZOMBIE,SkullType_113.ZOMBIE),
    PLAYER(SkullType_19.PLAYER,SkullType_113.PLAYER),
    CREEPER(SkullType_19.CREEPER,SkullType_113.CREEPER),
    ENDER_DRAGON(SkullType_19.DRAGON,SkullType_113.DRAGON)
    ;
    
    private final SkullDetails_18_19 skullDetails_18_19;
    private final SkullDetails_113 skullDetails_113;
    
    CompatibleSkullMaterial(SkullType_19 type19,SkullType_113 type113){
        skullDetails_18_19 = new SkullDetails_18_19(type19);
        skullDetails_113 = new SkullDetails_113(type113);
    }
    
    public Material getItemMaterial(){
        if(CompatibilityChecker.checkVersionAtLeast(1, 13)){
            return skullDetails_113.material;
        }else{
            return skullDetails_18_19.materialItem;
        }
    }
    public Material getBlockFloorMaterial(){
        if(CompatibilityChecker.checkVersionAtLeast(1, 13)){
            return skullDetails_113.material;
        }else{
            return skullDetails_18_19.materialBlock;
        }
    }
    public Material getBlockWallMaterial(){
        if(CompatibilityChecker.checkVersionAtLeast(1, 13)){
            return skullDetails_113.materialWall;
        }else{
            return skullDetails_18_19.materialBlock;
        }
    }
    public boolean isVariant(){
        if(CompatibilityChecker.checkVersionAtLeast(1, 13)){
            return false;
        }else{
            return skullDetails_18_19.materialBlock.name().equals("SKULL");
        }
    }
    public boolean isSkinnable(){
        return (this==CompatibleSkullMaterial.PLAYER);
    }
    
    public ItemStack getItemStack(int quantity){
        if(CompatibilityChecker.checkVersionAtLeast(1, 13)){
            return new ItemStack(skullDetails_113.material,quantity);
        }else{
            return new ItemStack(skullDetails_18_19.materialItem,quantity,skullDetails_18_19.datavalue);
        }
    }
    
    public CompatibleSkullMaterial get(ItemStack stack){
        Material mat = stack.getType();
        if(CompatibilityChecker.checkVersionAtLeast(1, 13)){
            //TODO: get by 113 material name
        }else{
            if(mat != SKELETON.getItemMaterial()) return null;//only SKULL_ITEM exists prior to 1.13
            //TODO: check damage value
            short dmg = stack.getDurability();//? I think this is it.
        }
    }
    
    public boolean isPlayerhead(ItemStack stack){
        
    }
    public boolean isHead(ItemStack stack){
        
    }
    
}
