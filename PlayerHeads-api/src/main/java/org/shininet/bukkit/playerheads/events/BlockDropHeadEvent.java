/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package org.shininet.bukkit.playerheads.events;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Event created by the PlayerHeads plugin when a block is broken by hand
 * (mined) and dropping a head. Note: does not occur when broken by water or
 * pistons.
 * @since 4.8.5-SNAPSHOT
 * @author crashdemons (crashenator at gmail.com)
 */
public class BlockDropHeadEvent extends BlockEvent implements Cancellable, DropHeadEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean canceled = false;
    private final ArrayList<ItemStack> itemDrops = new ArrayList<>();

    @Override
    public List<ItemStack> getDrops(){ return itemDrops; }
    
    /**
     * Construct the event
     *
     * @param block the block dropping the head
     * @param drop the head item being dropped
     */
    public BlockDropHeadEvent(final Block block, final ItemStack drop) {
        super(block);
        if(drop!=null) itemDrops.add(drop);
    }

    /**
     * Gets the item that will drop from the mined block.
     *
     * @return mutable ItemStack that will drop into the world once this event
     * is over
     * @deprecated Multiple items may drop, this method only retrieves the first.
     */
    @Deprecated
    @SuppressWarnings("unused")
    @Override
    public ItemStack getDrop() {
        if(itemDrops.isEmpty()) return null;
        return itemDrops.get(0);
    }
    
    /**
     * Sets the item that will drop from the mined block.
     * Note: this method clears any existing drops.
     * @since 5.2.0-SNAPSHOT
     * @param stack the stack to drop. If this is null, no item will be dropped, but the drop event will complete successfully as if one did. (cancel the event to stop the drop).
     */
    @Override
    public void setDrop(@Nullable final ItemStack stack){
        itemDrops.clear();
        if(stack==null) return;
        itemDrops.add(stack);
    }

    /**
     * Whether the event has been cancelled.
     *
     * @return Whether the event has been cancelled.
     */
    @Override
    public boolean isCancelled() {
        return canceled;
    }

    /**
     * Sets whether the event should be cancelled.
     *
     * @param cancel whether the event should be cancelled.
     */
    @Override
    public void setCancelled(final boolean cancel) {
        canceled = cancel;
    }

    /**
     * Get a list of handlers for the event.
     *
     * @return a list of handlers for the event
     */
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Get a list of handlers for the event.
     *
     * @return a list of handlers for the event
     */
    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
