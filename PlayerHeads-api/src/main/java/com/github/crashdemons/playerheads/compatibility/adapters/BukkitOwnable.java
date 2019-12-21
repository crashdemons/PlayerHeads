/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.adapters;

import com.github.crashdemons.playerheads.api.PlayerHeads;
import com.github.crashdemons.playerheads.compatibility.CompatibilityProvider;
import java.util.UUID;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Skull;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Constructs common head ownership object (adapter) from Skull BlockStates and skull ItemMeta, for ease of use.
 * @author crashdemons (crashenator at gmail.com)
 */
public class BukkitOwnable {
    private final Object baseSkull;
    public BukkitOwnable(Skull skullBlockState){
        baseSkull = skullBlockState;
    }
    public BukkitOwnable(SkullMeta skullMeta){
        baseSkull = skullMeta;
    }
    
    public String getOwnerDirect(){
        CompatibilityProvider provider = PlayerHeads.getApiInstance().getCompatibilityProvider();
        if(baseSkull instanceof Skull){
            return provider.getOwnerDirect((Skull)baseSkull);
        }else{
            return provider.getOwnerDirect((SkullMeta)baseSkull);
        }
    }
    public OfflinePlayer getOwningPlayerDirect(){
        CompatibilityProvider provider = PlayerHeads.getApiInstance().getCompatibilityProvider();
        if(baseSkull instanceof Skull){
            return provider.getOwningPlayerDirect((Skull)baseSkull);
        }else{
            return provider.getOwningPlayerDirect((SkullMeta)baseSkull);
        }
    }
    public String getOwner(){
        CompatibilityProvider provider = PlayerHeads.getApiInstance().getCompatibilityProvider();
        if(baseSkull instanceof Skull){
            return provider.getOwner((Skull)baseSkull);
        }else{
            return provider.getOwner((SkullMeta)baseSkull);
        }
    }
    public OfflinePlayer getOwningPlayer(){
        CompatibilityProvider provider = PlayerHeads.getApiInstance().getCompatibilityProvider();
        if(baseSkull instanceof Skull){
            return provider.getOwningPlayer((Skull)baseSkull);
        }else{
            return provider.getOwningPlayer((SkullMeta)baseSkull);
        }
    }
    
    
    public boolean setOwner(String ownerName){
        CompatibilityProvider provider = PlayerHeads.getApiInstance().getCompatibilityProvider();
        if(baseSkull instanceof Skull){
            return provider.setOwner((Skull)baseSkull,ownerName);
        }else{
            return provider.setOwner((SkullMeta)baseSkull,ownerName);
        }
    }
    public boolean setOwningPlayer(OfflinePlayer owner){
        CompatibilityProvider provider = PlayerHeads.getApiInstance().getCompatibilityProvider();
        if(baseSkull instanceof Skull){
            provider.setOwningPlayer((Skull)baseSkull,owner);
            return true;
        }else{
            return provider.setOwningPlayer((SkullMeta)baseSkull,owner);
        }
    }
    public boolean setProfile(UUID uuid, String texture){
        CompatibilityProvider provider = PlayerHeads.getApiInstance().getCompatibilityProvider();
        if(baseSkull instanceof Skull){
            return provider.setProfile((Skull) baseSkull, uuid, texture);
        }else{
            return provider.setProfile((SkullMeta) baseSkull, uuid, texture);
        }
    }
    
    public Object getBaseObject(){
        return baseSkull;
    }
}
