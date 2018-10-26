/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility;

import org.bukkit.Material;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
class SkullDetails_18_19 {
    public final Material materialItem;
    public final Material materialBlock;
    public final short datavalue;
    
    public SkullDetails_18_19(SkullType_19 skullType){
        if(skullType==null) skullType=SkullType_19.PLAYER;
        materialBlock=RuntimeReferences.getMaterialByName("SKULL");
        materialItem=RuntimeReferences.getMaterialByName("SKULL_ITEM");
        
        if(skullType==SkullType_19.DRAGON && CompatibilityChecker.checkVersionUnder(1, 9)){
            datavalue=(short) SkullType_19.PLAYER.ordinal();
        }else{
            datavalue=(short) skullType.ordinal();
        }
    }
}
