/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility;

import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public enum CompatibleGameRule {
    KEEP_INVENTORY("keepInventory")
    ;
    public final String keyName;
    CompatibleGameRule(String key){keyName=key;}
    
    public boolean getBool(World world){
        /*if(CompatibilityChecker.checkVersionAtLeast(1, 13)){//gamerule enum added in 1.13
            Class<?> c = World.class;                
            Method method = c.getDeclaredMethod("getGameRuleValue");
            method.invoke(equipment);
        }else{
            
        }*/
        //I would reflect this instead of using a deprecated method, but I can't reflect a method that has a type that may not exist yet (generic for that matter)
        return Boolean.valueOf(world.getGameRuleValue("keepInventory"));
    }
}
