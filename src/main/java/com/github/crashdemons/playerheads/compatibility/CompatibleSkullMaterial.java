/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility;


/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public enum CompatibleSkullMaterial {//should maintain compatibility with EntityType
    SKELETON(SkullType.SKELETON),
    WITHER_SKELETON(SkullType.WITHER_SKELETON),
    ZOMBIE(SkullType.ZOMBIE),
    PLAYER(SkullType.PLAYER),
    CREEPER(SkullType.CREEPER),
    ENDER_DRAGON(SkullType.DRAGON)
    ;
    
    private final SkullType skullType;
    private SkullDetails cachedDetails=null;
    
    CompatibleSkullMaterial(SkullType type){
        skullType=type;
    }
    public SkullDetails getDetails(){
        if(cachedDetails==null) cachedDetails=Compatibility.getProvider().getSkullDetails(skullType);
        return cachedDetails;
    }
    
    /*
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
    */
}
