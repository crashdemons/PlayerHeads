/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api;

import com.github.crashdemons.playerheads.TexturedSkullType;
import java.util.UUID;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class InternalHeadRepresentation extends HeadRepresentation{
    private final TexturedSkullType internalType;
    public InternalHeadRepresentation(TexturedSkullType type, String ownerName){
        super(type,ownerName);
        internalType=type;
    }
    public InternalHeadRepresentation(TexturedSkullType type, String ownerName, UUID ownerId){
        super(type,ownerName,ownerId);
        internalType=type;
    }

    public TexturedSkullType getInternalType() {
        return internalType;
    }
}
