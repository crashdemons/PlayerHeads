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
public class InternalHeadRepresentation implements HeadRepresentation{
    private final TexturedSkullType internalType;
    private final String ownerName;
    private final UUID ownerId;
    public InternalHeadRepresentation(TexturedSkullType type, String ownerName){
        this.internalType=type;
        this.ownerName=ownerName;
        this.ownerId=null;
    }
    public InternalHeadRepresentation(TexturedSkullType type, String ownerName, UUID ownerId){
        this.internalType=type;
        this.ownerName=ownerName;
        this.ownerId=ownerId;
    }

    public TexturedSkullType getInternalType() {
        return internalType;
    }
    
    @Override
    public HeadType getType() {
        return internalType;
    }

    @Override
    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public UUID getOwnerId() {
        return ownerId;
    }
    
    @Override
    public HeadComparisonResult compare(HeadRepresentation head){
        boolean typeEquality = this.getType().equals(head.getType());
        boolean ownerEquality = this.getOwnerId().equals(head.getOwnerId());
        if(this.getOwnerId()==null || head.getOwnerId()==null){
            ownerEquality = this.getOwnerName().equals(head.getOwnerName());
        }
        return HeadComparisonResult.fromEquality(typeEquality, ownerEquality);
    }
}
