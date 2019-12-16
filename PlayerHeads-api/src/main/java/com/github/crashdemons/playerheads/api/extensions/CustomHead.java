/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api.extensions;

import com.github.crashdemons.playerheads.api.HeadIdentity;
import com.github.crashdemons.playerheads.api.HeadType;
import com.github.crashdemons.playerheads.api.PlayerHeads;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.github.crashdemons.playerheads.api.HeadDisplay;

/**
 * Custom head type with information required to create, display, spawn, and
 * detect the head.
 *
 * @author crashdemons (crashenator at gmail.com)
 * @deprecated Draft API - partial implementation available only.
 * @since 5.3.0-SNAPSHOT
 */
@Deprecated
public class CustomHead extends HeadIdentity implements HeadDisplay {

    private final String spawnName;
    private final String displayName;
    private final String textureUrlB64;
    private final ArrayList<String> lore = null;


    /**
     * Constructs a custom head type with information required to create,
     * display, spawn, and detect the head. This class does not invoke any
     * Bukkit methods and only stores information, but it does require the API
     * to be available.
     *
     * @param uuid the internal ID used in the custom head's owner field, for
     * detection. This value should be unique to your head (randomly created),
     * but never change. It must not match other head UUIDs.
     * @param spawnName the string players can use to spawn your head with the
     * command /ph spawn #spawnnamehere
     * @param displayName the custom display-name (title) of the head displayed
     * as the item name and when clicking heads for information.
     * @param textureUrlB64 the base64-encoded texture json + URL
     * @param lore the loretext to display, or null for the default PlayerHeads loretext.
     */
    public CustomHead(final UUID uuid, final String spawnName, final String displayName, final String textureUrlB64, final List<String> lore,final String spawnString) {
        super(null, "", uuid,spawnString);
        this.spawnName = spawnName;
        this.displayName = displayName;
        this.textureUrlB64 = textureUrlB64;
        this.lore.addAll(lore);
    }
    
    /**
     * Constructs a custom head type with information required to create,
     * display, spawn, and detect the head. This class does not invoke any
     * Bukkit methods and only stores information, but it does require the API
     * to be available.
     *
     * @param uuid the internal ID used in the custom head's owner field, for
     * detection. This value should be unique to your head (randomly created),
     * but never change. It must not match other head UUIDs.
     * @param ownerName the owner name to set (optionally) for the head.
     * @param spawnName the string players can use to spawn your head with the
     * command /ph spawn #spawnnamehere
     * @param displayName the custom display-name (title) of the head displayed
     * as the item name and when clicking heads for information.
     * @param textureUrlB64 the base64-encoded texture json + URL
     * @param lore the loretext to display, or null for the default PlayerHeads loretext.
     * @deprecated custom heads should avoid using owner usernames whenever possible.
     */
    @Deprecated
    public CustomHead(final UUID uuid, final String ownerName, final String spawnName, final String displayName, final String textureUrlB64,final List<String> lore,final String spawnString) {
        super(null, ownerName, uuid, spawnString);
        this.spawnName = spawnName;
        this.displayName = displayName;
        this.textureUrlB64 = textureUrlB64;
        this.lore.addAll(lore);
    }

    @Override
    public HeadType getType() {
        return PlayerHeads.getApiInstance().getCustomHeadType();
    }
    

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getTexture() {
        return textureUrlB64;
    }
    
    @Override
    public ArrayList<String> getLore() {
        return lore;
    }
}
