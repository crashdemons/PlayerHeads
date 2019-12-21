/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.adapters;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityEvent;

/**
 * Constructs a common event object (adapter) that can retrieve location information.
 * @author crashdemons (crashenator at gmail.com)
 */
public class BukkitLocatableEvent {
    private final Event baseEvent;
    private final BukkitLocatable locatable;
    public BukkitLocatableEvent(EntityEvent event){
        baseEvent=event;
        locatable = new BukkitLocatable(event.getEntity());
    }
    public BukkitLocatableEvent(BlockEvent event){
        baseEvent=event;
        locatable = new BukkitLocatable(event.getBlock());
    }
    public BukkitLocatable getLocatable(){
        return locatable;
    }
    public Location getLocation(){
        return locatable.getLocation();
    }
}
