/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api.extensions;

import com.github.crashdemons.playerheads.api.HeadIdentity;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Provides registration of custom plugin-added heads to PlayerHeads.
 *
 * @author crashdemons (crashenator at gmail.com)
 * @deprecated Draft API - not implemented yet.
 */
@Deprecated
public final class HeadExtensionManager {

    
    private static final class ClassMappings { 
        public static final ConcurrentHashMap<HeadExtension, Plugin> HEAD_EXTENSIONS = new ConcurrentHashMap<>();
        public static final ConcurrentHashMap<HeadIdentifier, Plugin> HEAD_IDENTIFIERS = new ConcurrentHashMap<>();
        public static final ConcurrentHashMap<HeadUpdater, Plugin> HEAD_UPDATERS = new ConcurrentHashMap<>();
    }

    private static final class HeadMappings {

        public static final ConcurrentHashMap<UUID, CustomHead> HEAD_FROM_UUID = new ConcurrentHashMap<>();
        public static final ConcurrentHashMap<String, CustomHead> HEAD_FROM_SPAWN_STRING = new ConcurrentHashMap<>();
        public static final ConcurrentHashMap<UUID, HeadExtension> EXTENSION_FROM_UUID = new ConcurrentHashMap<>();
    }

    private HeadExtensionManager() {
    }
    //--------------------------------------------------
    
    public static HeadIdentity identifyHead(final EntityType type){
        HeadIdentity hr = null;
        for(HeadIdentifier identifier : getIdentifiers()){
            hr = identifier.identifyHead(type);
            if(hr!=null) return hr;
        }
        return hr;
    }
    public static HeadIdentity identifyHead(final Entity entity){
        HeadIdentity hr = null;
        for(HeadIdentifier identifier : getIdentifiers()){
            hr = identifier.identifyHead(entity);
            if(hr!=null) return hr;
        }
        return hr;
    }
    public static HeadIdentity identifyHead(final ItemStack item){
        HeadIdentity hr = null;
        for(HeadIdentifier identifier : getIdentifiers()){
            hr = identifier.identifyHead(item);
            if(hr!=null) return hr;
        }
        return hr;
    }
    public static HeadIdentity identifyHead(final BlockState block){
        HeadIdentity hr = null;
        for(HeadIdentifier identifier : getIdentifiers()){
            hr = identifier.identifyHead(block);
            if(hr!=null) return hr;
        }
        return hr;
    }
    
    
    public static HeadIdentity updateHead(final HeadIdentity head){
        HeadIdentity newHead = head;
        for(HeadUpdater up : getUpdaters()){
            newHead = up.updateHead(newHead);
        }
        return newHead;
    }

    //--------------------------------------------------
    public static boolean registerExtension(final Plugin plugin, final HeadExtension extension) {
        if(getExtensions().contains(extension)){
            return false;
        }
        ClassMappings.HEAD_EXTENSIONS.put(extension, plugin);
        return true;
    }
    
    public static synchronized boolean registerIdentifier(final Plugin plugin, final HeadIdentifier identifier){
        if(getIdentifiers().contains(identifier)){
            return false;
        }
        ClassMappings.HEAD_IDENTIFIERS.put(identifier, plugin);
        return true;
    }
    public static synchronized boolean registerUpdater(final Plugin plugin, final HeadUpdater updater){
        if(getUpdaters().contains(updater)){
            return false;
        }
        ClassMappings.HEAD_UPDATERS.put(updater, plugin);
        return true;
    }

    public static ArrayList<HeadExtension> getExtensions() {
        return new ArrayList<>(ClassMappings.HEAD_EXTENSIONS.keySet());
    }
    public static ArrayList<HeadIdentifier> getIdentifiers() {
        return new ArrayList<>(ClassMappings.HEAD_IDENTIFIERS.keySet());
    }
    public static ArrayList<HeadUpdater> getUpdaters() {
        return new ArrayList<>(ClassMappings.HEAD_UPDATERS.keySet());
    }
    
    //-----------------------------------------------------------
    public static HeadExtension getHeadHandler(UUID id){
        if(id==null){
            return null;
        }
        return HeadMappings.EXTENSION_FROM_UUID.get(id);
    }

    //------------------------------------------------------------
    /**
     * Register preset heads added by your extension that should replace heads with a matching UUID.
     * Additionally, any registered head uuid will be identified the headtype defined by the representation (typically "CUSTOM")
     * If you want to add a dynamic number of heads (eg: based on player id), consider using a different operation.
     * @param ext the extension adding the head
     * @param head the head representation to register
     * @return whether the head could be registered.
     */
    public static synchronized boolean registerHeadRepresentation(final HeadExtension ext, final CustomHead head) {
        UUID id = head.getOwnerId();
        String spawn = head.getSpawnName();
        if (HeadMappings.HEAD_FROM_UUID.get(id) != null) {
            return false;
        }
        if (HeadMappings.HEAD_FROM_SPAWN_STRING.get(spawn) != null) {
            return false;
        }
        if (HeadMappings.EXTENSION_FROM_UUID.get(id) != null) {
            return false;
        }
        HeadMappings.HEAD_FROM_UUID.put(id, head);
        HeadMappings.HEAD_FROM_SPAWN_STRING.put(spawn, head);
        HeadMappings.EXTENSION_FROM_UUID.put(id, ext);
        return true;
    }

    public static CustomHead getHeadByOwner(final UUID owner) {
        if (owner == null) {
            return null;
        }
        return HeadMappings.HEAD_FROM_UUID.get(owner);
    }

    public static CustomHead getHeadBySpawnString(final String spawnName) {
        if (spawnName == null) {
            return null;
        }
        return HeadMappings.HEAD_FROM_SPAWN_STRING.get(spawnName);
    }
}
