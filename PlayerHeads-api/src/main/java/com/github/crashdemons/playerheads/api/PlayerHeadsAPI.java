/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.crashdemons.playerheads.api;

import com.github.crashdemons.playerheads.compatibility.CompatibilityProvider;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.shininet.bukkit.playerheads.PlayerHeadsPlugin;

/**
 * Interface for the PlayerHeads API. This interface defines each method that
 * the PlayerHeads API provides.
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public interface PlayerHeadsAPI {

    /**
     * Get the main PlayerHeads Plugin instance
     *
     * @return the PlayerHeads plugin object, or null if unavailable.
     */
    @Nullable
    public PlayerHeadsPlugin getPlugin();

    /**
     * Get the version of the PlayerHeads plugin in use
     *
     * @return the version of the PlayerHeads plugin in use
     */
    @Nullable
    public String getVersion();

    /**
     * Gets the type of head associated with an itemstack.
     *
     * @param s the itemstack to check
     * @return the type of head, or null if there is none
     */
    @Nullable
    public HeadType getHeadFrom(@NotNull ItemStack s);

    /**
     * Gets the type of head associated with the Block
     *
     * @param s the blockstate to check
     * @return the type of head, or null if the block wasn't a head.
     */
    @Nullable
    public HeadType getHeadFrom(@NotNull BlockState s);

    /**
     * Gets the type of head associated with an entity
     *
     * @param e the entity to check
     * @return the type of head, or null if there is no viable head.
     */
    @Nullable
    public HeadType getHeadOf(@NotNull Entity e);

    /**
     * Gets the type of head associated with an entity-type. See deprecation
     * note for limitations.
     *
     * @param t the entity type to check
     * @deprecated this may not do exactly what you want because between MC
     * 1.10-1.13 many entities were separated from variants to their own
     * entity-type. Using this method will introduce version-dependent behavior
     * (For example: this may not be able to tell a stray from a skeleton in
     * certain server versions) and should be avoided by using
     * getHeadFrom(Entity) instead.
     * @see #getHeadDrop(org.bukkit.entity.Entity)
     * @return the type of head, or null if there is no viable head (unsupported mob)
     */
    @Deprecated
    @Nullable
    public HeadType getHeadOf(@NotNull EntityType t);

    /**
     * Gets a stack of head items for the given type and amount. Note:
     * player-type heads will be untextured and have no set owner with this
     * method.
     * 
     * This is affected by configuration settings like "dropvanillaheads" and "addlore".

     * @param h the head type to create items of
     * @param num the number of items to create
     * @return The item stack, or null if the head type was null
     */
    @Nullable
    public ItemStack getHeadItem(@NotNull HeadType h, int num);

    /**
     * Gets a stack of head items as they would normally be dropped from the
     * given entity. Note: this method takes into full account plugin
     * configuration settings, player's name, and preset item drop amount
     *
     * @param e the entity to create items from
     * @return The item stack, or null if the entity head type was null (unsupported mob).
     */
    @Nullable
    public ItemStack getHeadDrop(@NotNull Entity e);
    
    //5.1.1 API
    
    /**
     * Gets a "boring" playerhead without an owner as if "dropboringplayerheads" was enabled.
     * This head is provided without manipulation by configuration settings except 'addlore'.
     * @since 5.1.1-SNAPSHOT
     * @param num number of head items to get
     * @return the item stack requested
     */
    @NotNull
    public ItemStack getBoringPlayerheadItem(int num);
    
    /**
     * Gets the playerhead for the user provided.
     * This head is provided subject to configuration settings like 'dropboringplayerheads' and 'addlore'
     * @since 5.1.1-SNAPSHOT
     * @param username the username for the playerhead
     * @param num number of items to include of the head
     * @param forceOwner if true, ignore the state of 'dropboringplayerheads' and provide a head for the owner anyway
     * @return the itemstack requested
     */
    @NotNull
    public ItemStack getHeadItem(@NotNull String username, int num, boolean forceOwner);
    
    /**
     * Gets the playerhead for the user provided.
     * This head is provided subject to configuration settings like 'dropboringplayerheads' and 'addlore'
     * Note: this is based on username lookup.
     * @since 5.1.1-SNAPSHOT
     * @param player the player to retrieve a head for
     * @param num number of items to include of the head
     * @param forceOwner if true, ignore the state of 'dropboringplayerheads' and provide a head for the owner anyway
     * @return the itemstack requested, or null if the username could not be determined.
     */
    @Nullable
    public ItemStack getHeadItem(OfflinePlayer player, int num, boolean forceOwner);
    
    
    /**
     * Gets a head item based on the 'spawn' string used in PlayerHeads (like #zombie or playername).
     * This head is provided subject to configuration settings like 'dropboringplayerheads' and 'addlore'
     * @param spawnName the spawn string (#mobname or username)
     * @param num the number of heads to provide in the stack
     * @param forceOwner if true, ignore the state of 'dropboringplayerheads' and provide head information for the owner anyway
     * @return the stack of heads, or null
     */
    @Nullable
    public ItemStack getHeadItemFromSpawnString(String spawnName, int num, boolean forceOwner);
    
    
    /**
     * Gets an instance of a class that provides many useful crossversion PlayerHeads capabilities that would normally be version-specific.
     * For example: viewing and editing ownership information for skinned playerheads.
     * These may be more subject to change than other API methods, so you should avoid them except when necessary.
     * @since 5.1.1-SNAPSHOT
     * @return the CompatibilityProvider instance or null if PlayerHeads plugin was not initialized yet.
     * @deprecated although this is provided and maintained, it is subject to change with plugin requirements, so you should avoid using it normally.
     */
    @Deprecated
    @Nullable
    public CompatibilityProvider getCompatibilityProvider();
    
    //5.2.2 API;
    /**
     * Gets the details (type and owner) of a head for comparison and re-creation purposes the head represented by a 'spawn' string (like #zombie or playername).
     * This is NOT subject to configuration settings except for 'dropboringplayerheads'
     * @param spawnName the spawn string (#mobname or username)
     * @param forceOwner if true, ignore the state of 'dropboringplayerheads' and provide head information for the owner anyway
     * @return the representation details for the head
     * @since 5.3.0-SNAPSHOT
     */
    public HeadRepresentation getHeadRepresentationFromSpawnString(String spawnName, boolean forceOwner);
    /**
     * Gets the details (type and owner) of a head for comparison and re-creation purposes for an entity's head.
     * This is NOT subject to configuration settings except for 'dropboringplayerheads'
     * @param e the entity to get the head for
     * @return the representation details for the head
     * @since 5.3.0-SNAPSHOT
     */
    @Nullable public HeadRepresentation getHeadRepresentation(@NotNull Entity e);
    /**
     * Gets the details (type and owner) of a head for comparison and re-creation purposes for a user's head.
     * This is NOT subject to configuration settings except for 'dropboringplayerheads'
     * @param username the username to get the head for.
     * @param forceOwner if true, ignore the state of 'dropboringplayerheads' and provide head information for the owner anyway
     * @return the representation details for the head
     * @since 5.3.0-SNAPSHOT
     */
    @NotNull public HeadRepresentation getHeadRepresentation(@NotNull String username, boolean forceOwner);
    /**
     * Gets the details (type and owner) of a head for comparison and re-creation purposes for a user's head.
     * This is NOT subject to configuration settings except for 'dropboringplayerheads'
     * @param player the player to get the head for.
     * @param forceOwner if true, ignore the state of 'dropboringplayerheads' and provide head information for the owner anyway
     * @return the representation details for the head
     */
    @NotNull public HeadRepresentation getHeadRepresentation(@NotNull OfflinePlayer player, boolean forceOwner);
    /**
     * Gets the details (type and owner) of a head for comparison and re-creation purposes for a "boring playerhead" as if 'dropboringplayerheads' was enabled.
     * @return the representation details for the head
     * @since 5.3.0-SNAPSHOT
     */
    @NotNull public HeadRepresentation getHeadRepresentationFromBoringPlayerhead();
    /**
     * Gets the details (type and owner) of a head for comparison and re-creation purposes.
     * @param stack the item stack to determine head information
     * @return the head representation.
     * @since 5.3.0-SNAPSHOT
     */
    @Nullable public HeadRepresentation getHeadRepresentation(@NotNull ItemStack stack);
    /**
     * Gets the details (type and owner) of a head for comparison and re-creation purposes.
     * @param state the block-state to determine head information
     * @return the head representation.
     * @since 5.3.0-SNAPSHOT
     */
    @Nullable public HeadRepresentation getHeadRepresentation(@NotNull BlockState state);
    /**
     * Create head items from the head representation details (type and owner).
     * This is subject to configuration options like 'addlore', however ownership information will override 'dropboringplayerheads'
     * @param head the details defining the head
     * @param num the number of heads to create in the stack.
     * @return the item based on the head representation.
     * @since 5.3.0-SNAPSHOT
     */
    @NotNull public ItemStack getHeadItem(HeadRepresentation head, int num);
    @NotNull public HeadType getCustomHeadType();
    
    //5.3 API
    /**
     * Gets the details (type and owner) of a head for comparison and re-creation purposes for an entitytype's head.
     * This is NOT subject to configuration settings except for 'dropboringplayerheads'
     * @param et the entity-type to get the head for
     * @return the representation details for the head
     * @since 5.3.0-SNAPSHOT
     * @deprecated this may not do exactly what you want because between MC
     * 1.10-1.13 many entities were separated from variants to their own. Also,
     * this foregoes any head ownership information beyond the that defined by the type of mob/head.
     */
    @Deprecated
    @Nullable public HeadRepresentation getHeadRepresentation(@NotNull EntityType et);
    /**
     * Gets the details (type and owner) of a head for comparison and re-creation purposes for an inbuilt HeadType.
     * This is NOT subject to configuration settings except for 'dropboringplayerheads'.
     * Note: this method only converts the information associated with the preset head-type and does not represent any extended mob ownership information.
     * @param ht the head-type to get the head for
     * @return the representation details for the head
     * @since 5.3.0-SNAPSHOT
     */
    @Nullable public HeadRepresentation getHeadRepresentation(@NotNull HeadType ht);
}
