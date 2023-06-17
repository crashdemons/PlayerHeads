/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.craftbukkit_1_14;

import com.github.crashdemons.playerheads.compatibility.CompatibilityProvider;
import com.github.crashdemons.playerheads.compatibility.RuntimeReferences;
import com.github.crashdemons.playerheads.compatibility.SkullType;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

/**
 * CompatibilityProvider Implementation for 1.13+ support
 * @author crashdemons (crashenator at gmail.com)
 */
@SuppressWarnings( "deprecation" )
public class Provider extends com.github.crashdemons.playerheads.compatibility.craftbukkit_1_13.Provider implements CompatibilityProvider {
    public Provider(){}
    @Override public String getType(){ return "craftbukkit"; }
    @Override public String getVersion(){ return "1.14+"; }
    
    
    
    @Override
    protected boolean setPersistentTag(Entity entity, Plugin plugin, String key, String value){
        NamespacedKey nskey = new NamespacedKey(plugin,key);
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        pdc.set(nskey, PersistentDataType.STRING, value);
        return true;
    }
    @Override
    protected String getPersistentTag(Entity entity, Plugin plugin, String key){
        NamespacedKey nskey = new NamespacedKey(plugin,key);
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        return pdc.get(nskey, PersistentDataType.STRING);
    }
    
    @Override
    public boolean supportsEntityTagType(boolean persistent){
        return true;
    }
    
    @Override
    protected SkullType getSkullType(Material mat){
        String typeName = mat.name();
        typeName=typeName.replaceFirst("_WALL", "").replaceFirst("_HEAD", "").replaceFirst("_SKULL", "");
        return RuntimeReferences.getSkullTypeByName(typeName);
    }
    
    protected org.bukkit.SkullType adaptSkullType(SkullType compatType){
        if(compatType==SkullType.PIGLIN && Version.checkUnder(1, 9)) return org.bukkit.SkullType.PLAYER;
        try{
            return org.bukkit.SkullType.values()[compatType.ordinal()];
        }catch(Exception e){
            return null;
        }
    }
    

}
