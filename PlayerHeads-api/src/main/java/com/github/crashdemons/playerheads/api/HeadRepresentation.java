/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api;

import java.util.UUID;
import org.jetbrains.annotations.Nullable;

/**
 * Interface for an object containing the information that is used to compare and construct a head based on type and owner.
 * This information explicitly excludes details like the texture which are not considered part of head equivalence.
 * @author crashdemons (crashenator at gmail.com)
 * @since 5.2.2-SNAPSHOT
 */
public interface HeadRepresentation {
    @Nullable public UUID getOwnerId();
    @Nullable public String getOwnerName();
    @Nullable public HeadType getType();
    @Nullable public HeadComparisonResult compare(HeadRepresentation head); 
}
