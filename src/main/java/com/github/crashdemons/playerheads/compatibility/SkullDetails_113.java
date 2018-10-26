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
class SkullDetails_113 {
    Material material;
    Material materialWall;
    public SkullDetails_113(SkullType_113 type){
        if(type==null){
            material=RuntimeReferences.getMaterialByName("PLAYER_HEAD");
            materialWall=RuntimeReferences.getMaterialByName("PLAYER_WALL_HEAD");
        }
        String typeName=type.name();
        String suffix=type.isSkull? "_SKULL" : "_HEAD";
        material=RuntimeReferences.getMaterialByName(typeName+suffix);
        materialWall=RuntimeReferences.getMaterialByName(typeName+"_WALL"+suffix);
        if(material==null){
            material=RuntimeReferences.getMaterialByName("PLAYER_HEAD");
            materialWall=RuntimeReferences.getMaterialByName("PLAYER_WALL_HEAD");
        }
    }
}
