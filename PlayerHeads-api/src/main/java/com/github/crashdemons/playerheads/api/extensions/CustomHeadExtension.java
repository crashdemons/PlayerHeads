/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api.extensions;

import com.github.crashdemons.playerheads.api.HeadRepresentation;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Interface defining methods for identifying and configuring custom heads you are registering.
 * @author crashdemons (crashenator at gmail.com)
 */
public interface CustomHeadExtension {
    public boolean isHandledHead(HeadRepresentation hr);
    @Nullable public HeadRepresentation identifyHead(Entity e);
    @Nullable public HeadRepresentation identifyHead(ItemStack s);
    @Nullable public HeadRepresentation identifyHead(BlockState s);
    @NotNull public double getDropRate(CustomHeadRepresentation hr);
}
