/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
package org.shininet.bukkit.playerheads.events;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Event created by the PlayerHeads plugin when a [living] entity is beheaded.
 *
 * This class will usually be instanced as either MobDropHeadEvent (for mobs) or
 * PlayerDropHeadEvent (for a Player).
 *
 * Cancellable.
 *
 * <i>Note:</i> Some of this documentation was inferred after the fact and may
 * be inaccurate.
 * @since 3.11
 * @author meiskam
 */
public class LivingEntityDropHeadEvent extends EntityEvent implements Cancellable, DropHeadEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean canceled = false;
    private final ArrayList<ItemStack> itemDrops = new ArrayList<>();
    
    @Override
    public List<ItemStack> getDrops(){ return itemDrops; }

    /**
     * Construct the event
     *
     * @param entity the [living] entity droping the head
     * @param drop the head item being dropped
     */
    LivingEntityDropHeadEvent(final LivingEntity entity, final ItemStack drop) {
        super(entity);
        itemDrops.add(drop);
    }

    /**
     * Gets the item that will drop from the beheading.
     *
     * @return mutable ItemStack that will drop into the world once this event
     * is over
     * @deprecated This method only gets one drop item, there may be multiple
     */
    @SuppressWarnings("unused")
    @Deprecated
    @Override
    public ItemStack getDrop() {
        if(itemDrops.isEmpty()) return null;
        return itemDrops.get(0);
    }
    
    /**
     * Sets the item to drop for the beheading.
     * Note: this method clears any existing drops.
     * @since 5.2.0-SNAPSHOT
     * @param stack  The stack to drop. If this is null, no item will be dropped, but the drop event will complete successfully as if one did. (cancel the event to stop the drop).
     */
    @Override
    public void setDrop(@Nullable final ItemStack stack){
        itemDrops.clear();
        if(stack==null) return;
        itemDrops.add(stack);
    }

    /**
     * Gets the entity that was beheaded
     *
     * @return the beheaded entity
     */
    @Override
    public LivingEntity getEntity() {
        return (LivingEntity) entity;
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
