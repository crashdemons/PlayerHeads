/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api.extensions;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.plugin.Plugin;

/**
 * Provides registration of custom plugin-added heads to PlayerHeads.
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class ExtensionManager {

    private static final ConcurrentHashMap<CustomHeadExtension,Plugin> headExtensions =  new ConcurrentHashMap<>();
    
    private static class HeadMappings {
        public static final ConcurrentHashMap<UUID, CustomHeadRepresentation> fromUUID =  new ConcurrentHashMap<>();
        public static final ConcurrentHashMap<String, CustomHeadRepresentation> fromSpawnString = new ConcurrentHashMap<>();
    }

    private ExtensionManager() {
    }

    //--------------------------------------------------
    
    public static void registerHeadExtension(Plugin plugin, CustomHeadExtension identifier){
        headExtensions.put(identifier, plugin);
    }
    
    public static ArrayList<CustomHeadExtension> getHeadExtensions(){
        return new ArrayList<>(headExtensions.keySet());
    }
    
    public static getHeadDropRate(){//loop through all and check for rate!
        
    }
    
    
    //------------------------------------------------------------
    
    public static synchronized boolean registerHead(CustomHeadRepresentation head) {
        UUID id = head.getOwnerId();
        String spawn = head.getSpawnName();
        if (HeadMappings.fromUUID.get(id) != null) {
            return false;
        }
        if (HeadMappings.fromSpawnString.get(spawn) != null) {
            return false;
        }
        HeadMappings.fromUUID.put(id, head);
        HeadMappings.fromSpawnString.put(spawn, head);
        return true;
    }

    public static CustomHeadRepresentation getHeadByOwner(UUID owner) {
        return HeadMappings.fromUUID.get(owner);
    }

    public static CustomHeadRepresentation getHeadBySpawnString(String spawnName) {
        return HeadMappings.fromSpawnString.get(spawnName);
    }
}
