/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api.extensions;

import com.github.crashdemons.playerheads.api.HeadRepresentation;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Interface defining a class that can identify heads based on custom logic.
 * Warning: The methods defined in this class are called for every identification of a head for the given argument, so registering an identifier may impact performance.
 * @author crashdemons (crashenator at gmail.com)
 * @deprecated draft api - not fully implemented
 */
@Deprecated
public interface HeadIdentifier {
    @Nullable public  HeadRepresentation identifyHead(final EntityType t);
    @Nullable public  HeadRepresentation identifyHead(final Entity e);
    @Nullable public  HeadRepresentation identifyHead(final ItemStack s);
    @Nullable public  HeadRepresentation identifyHead(final BlockState s);
}
