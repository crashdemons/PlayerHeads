/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api.extensions;

import com.github.crashdemons.playerheads.api.HeadRepresentation;
import com.github.crashdemons.playerheads.api.HeadType;
import com.github.crashdemons.playerheads.api.PlayerHeads;
import java.util.UUID;

/**
 * Custom head type with information required to create, display, spawn, and detect the head.
 * @author crashdemons (crashenator at gmail.com)
 */
public class CustomHeadRepresentation extends HeadRepresentation{
    private final String spawnName;
    private final String displayName;
    private final String textureUrlB64;
    
    /**
     * Constructs a custom head type with information required to create, display, spawn, and detect the head.
     * This class does not invoke any Bukkit methods and only stores information, but it does require the API to be available.
     * @param uuid the internal ID used in the custom head's owner field, for detection. This value should be unique to your head (randomly created), but never change. It must not match other head UUIDs.
     * @param spawnName the string players can use to spawn your head with the command /ph spawn #spawnnamehere
     * @param displayName the custom display-name (title) of the head displayed as the item name and when clicking heads for information.
     * @param textureUrlB64 the base64-encoded texture json + URL
     */
    public CustomHeadRepresentation(UUID uuid, String spawnName, String displayName, String textureUrlB64){
        super(null,"",uuid);
        this.spawnName=spawnName;
        this.displayName=displayName;
        this.textureUrlB64=textureUrlB64;
    }

    @Override
    public String getOwnerName(){//custom heads don't have owner usernames, only id's
        return "";
    }
    
    @Override
    public HeadType getType(){
        return PlayerHeads.getApiInstance().getCustomHeadType();
    }

    public String getSpawnName() {
        return spawnName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getTexture() {
        return textureUrlB64;
    }
    
    
    
}
