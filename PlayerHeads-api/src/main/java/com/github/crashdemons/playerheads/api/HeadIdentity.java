/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class defining the "identity" of a head, used for for comparing and constructing a
 * head based on type and owner. This information explicitly
 * excludes details like the texture which are not considered part of head
 * equivalence.
 *
 * @author crashdemons (crashenator at gmail.com)
 * @since 5.3.0-SNAPSHOT
 */
public class HeadIdentity {

    //@Nullable public abstract UUID getOwnerId();
    //@Nullable public abstract String getOwnerName();
    //@Nullable public abstract HeadType getType();
    //@Nullable public abstract HeadComparisonResult compare(HeadRepresentation head); 
    private final PHHeadType type;
    private final String ownerName;
    private final UUID ownerId;
    private final String spawnString; //optional

    public HeadIdentity(final PHHeadType type, final String ownerName) {
        this.type = type;
        this.ownerName = ownerName;
        this.ownerId = null;
        this.spawnString="";
    }

    public HeadIdentity(final PHHeadType type, final String ownerName, final UUID ownerId) {
        this.type = type;
        this.ownerName = ownerName;
        this.ownerId = ownerId;
        this.spawnString="";
    }
    
    public HeadIdentity(final PHHeadType type, final String ownerName,final String spawnString) {
        this.type = type;
        this.ownerName = ownerName;
        this.ownerId = null;
        this.spawnString=spawnString;
    }

    public HeadIdentity(final PHHeadType type, final String ownerName, final UUID ownerId,final String spawnString) {
        this.type = type;
        this.ownerName = ownerName;
        this.ownerId = ownerId;
        this.spawnString=spawnString;
    }
    
    
    /**
     * Get the "spawn" name for the associated skulltype, as defined in the
     * "lang" file.
     * <p>
     * This string is used to spawn-in the skull in external commands.
     *
     * @return A string containing the spawnname.
     */
    public String getSpawnString() {
        return spawnString;
    }
    

    @Nullable
    public PHHeadType getType() {
        return type;
    }

    @Nullable
    public String getOwnerName() {
        return ownerName;
    }

    @Nullable
    public UUID getOwnerId() {
        return ownerId;
    }

    @NotNull
    public HeadComparisonResult compare(final HeadIdentity head) {
        boolean typeEquality = this.getType().equals(head.getType());
        boolean ownerEquality = this.getOwnerId().equals(head.getOwnerId());
        if (this.getOwnerId() == null || head.getOwnerId() == null) {
            ownerEquality = this.getOwnerName().equals(head.getOwnerName());
        }
        return HeadComparisonResult.fromEquality(typeEquality, ownerEquality);
    }
}
