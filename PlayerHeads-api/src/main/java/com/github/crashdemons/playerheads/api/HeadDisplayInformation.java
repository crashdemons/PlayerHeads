/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api;

import org.jetbrains.annotations.NotNull;

/**
 * Interface defining display details (names and appearance) of heads which are
 * not used in their identity / comparison.
 * @author crashdemons (crashenator at gmail.com)
 * @since 5.3.0-SNAPSHOT
 */
public interface HeadDisplayInformation {
    /**
     * Get the "spawn" name for the associated skulltype, as defined in the
     * "lang" file.
     * <p>
     * This string is used to spawn-in the skull in external commands.
     *
     * @return A string containing the spawnname.
     */
    public String getSpawnName();

    /**
     * Gets the item displayname for the associated skulltype, as defined in the
     * "lang" file.
     * 
     * Originally directly part of HeadType until 5.3
     * @return A string containing the skulltype's displayname
     */
    @NotNull
    public String getDisplayName();
    
    /**
     * Get the Base64-encoded texture string associated with the skulltype
     *
     * Originally directly part of HeadType until 5.3
     * @return A base64 string
     */
    @NotNull
    public String getTexture();
}