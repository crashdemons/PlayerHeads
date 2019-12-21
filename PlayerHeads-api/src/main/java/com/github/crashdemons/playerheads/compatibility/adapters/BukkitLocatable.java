/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.adapters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * Constructs a common object (adapter) that has location information from bukkit objects with getLocation
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class BukkitLocatable {

    Object baseObject;

    public BukkitLocatable(Entity entity) {
        baseObject = entity;
    }
    public BukkitLocatable(Block block) {
        baseObject = block;
    }
    public BukkitLocatable(BlockState blockState) {
        baseObject = blockState;
    }

    @NotNull
    public Location getLocation() {
        try {
            Method method = baseObject.getClass().getMethod("getLocation");
            return (Location) method.invoke(baseObject);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            throw new IllegalStateException("Locatable bukkit object does not support getLocation invocation.", e);
        }
    }
}
