/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.faketestserver_1_0;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class CompatibleProfileTEST implements PlayerProfile {
    private UUID uuid;
    private String username;
    private PlayerTextures textures=null;

    public CompatibleProfileTEST(@NotNull UUID id, @NotNull  String username){
        this.uuid=id;
        this.username=username;
    }

    @Nullable
    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Nullable
    @Override
    public String getName() {
        return username;
    }

    @NotNull
    @Override
    public PlayerTextures getTextures() {
        return textures;
    }

    @Override
    public void setTextures(@Nullable PlayerTextures playerTextures) {
        this.textures=playerTextures;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public CompletableFuture<PlayerProfile> update() {
        return null;
    }

    @Override
    public PlayerProfile clone() {
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        return null;
    }
}
