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
 * Interface for an object containing the information that is used to compare
 * and construct a head based on type and owner. This information explicitly
 * excludes details like the texture which are not considered part of head
 * equivalence.
 *
 * @author crashdemons (crashenator at gmail.com)
 * @since 5.2.2-SNAPSHOT
 */
public class HeadRepresentation {

    //@Nullable public abstract UUID getOwnerId();
    //@Nullable public abstract String getOwnerName();
    //@Nullable public abstract HeadType getType();
    //@Nullable public abstract HeadComparisonResult compare(HeadRepresentation head); 
    private final HeadType type;
    private final String ownerName;
    private final UUID ownerId;

    public HeadRepresentation(final HeadType type, final String ownerName) {
        this.type = type;
        this.ownerName = ownerName;
        this.ownerId = null;
    }

    public HeadRepresentation(final HeadType type, final String ownerName, final UUID ownerId) {
        this.type = type;
        this.ownerName = ownerName;
        this.ownerId = ownerId;
    }

    @Nullable
    public HeadType getType() {
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
    public HeadComparisonResult compare(final HeadRepresentation head) {
        boolean typeEquality = this.getType().equals(head.getType());
        boolean ownerEquality = this.getOwnerId().equals(head.getOwnerId());
        if (this.getOwnerId() == null || head.getOwnerId() == null) {
            ownerEquality = this.getOwnerName().equals(head.getOwnerName());
        }
        return HeadComparisonResult.fromEquality(typeEquality, ownerEquality);
    }
}
