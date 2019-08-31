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
 * @deprecated Draft API - not implemented yet.
 */
@Deprecated
public final class ExtensionManager {

    private static final ConcurrentHashMap<CustomHeadExtension, Plugin> HEAD_EXTENSIONS = new ConcurrentHashMap<>();

    private static final class HeadMappings {

        public static final ConcurrentHashMap<UUID, CustomHeadRepresentation> FROM_UUID = new ConcurrentHashMap<>();
        public static final ConcurrentHashMap<String, CustomHeadRepresentation> FROM_SPAWN_STRING = new ConcurrentHashMap<>();
    }

    private ExtensionManager() {
    }

    //--------------------------------------------------
    public static void registerHeadExtension(final Plugin plugin, final CustomHeadExtension identifier) {
        HEAD_EXTENSIONS.put(identifier, plugin);
    }

    public static ArrayList<CustomHeadExtension> getHeadExtensions() {
        return new ArrayList<>(HEAD_EXTENSIONS.keySet());
    }

    public static Double getHeadDropRate() {//loop through all and check for rate!

        return null;
    }

    //------------------------------------------------------------
    public static synchronized boolean registerHead(final CustomHeadRepresentation head) {
        UUID id = head.getOwnerId();
        String spawn = head.getSpawnName();
        if (HeadMappings.FROM_UUID.get(id) != null) {
            return false;
        }
        if (HeadMappings.FROM_SPAWN_STRING.get(spawn) != null) {
            return false;
        }
        HeadMappings.FROM_UUID.put(id, head);
        HeadMappings.FROM_SPAWN_STRING.put(spawn, head);
        return true;
    }

    public static CustomHeadRepresentation getHeadByOwner(final UUID owner) {
        if (owner == null) {
            return null;
        }
        return HeadMappings.FROM_UUID.get(owner);
    }

    public static CustomHeadRepresentation getHeadBySpawnString(final String spawnName) {
        if (spawnName == null) {
            return null;
        }
        return HeadMappings.FROM_SPAWN_STRING.get(spawnName);
    }
}
