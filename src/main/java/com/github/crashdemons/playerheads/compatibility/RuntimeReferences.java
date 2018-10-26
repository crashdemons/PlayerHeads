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
public class RuntimeReferences {
    private RuntimeReferences(){}
    public static Material getMaterialByName(String name){
        try{
            return Material.valueOf(name);
        }catch(IllegalArgumentException e){
            return null;
        }
    }
    public static SkullType_19 getSkullType19ByName(String name){
        try{
            return SkullType_19.valueOf(name);
        }catch(IllegalArgumentException e){
            return null;
        }
    }
}
