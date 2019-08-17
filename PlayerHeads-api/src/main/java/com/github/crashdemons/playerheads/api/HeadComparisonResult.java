/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api;

/**
 * Results of comparing two heads
 * @author crashdemons (crashenator at gmail.com)
 */
public enum HeadComparisonResult {
    SAME_TYPE_AND_OWNER,
    SAME_TYPE,
    SAME_OWNER,
    NO_SIMILARITY;

    public boolean isSameOwner(){
        return (this==HeadComparisonResult.SAME_TYPE_AND_OWNER || this==HeadComparisonResult.SAME_OWNER);
    }
    public boolean isSameType(){
        return (this==HeadComparisonResult.SAME_TYPE_AND_OWNER || this==HeadComparisonResult.SAME_TYPE);
    }
    public boolean isSame(){
        return this==HeadComparisonResult.SAME_TYPE_AND_OWNER;
    }
    
    public HeadComparisonResult add(HeadComparisonResult result){
        boolean typeEquality = this.isSameType() || result.isSameType();
        boolean ownerEquality = this.isSameOwner() || result.isSameOwner();
        return fromEquality(typeEquality,ownerEquality);
    }
    
    public HeadComparisonResult fromEquality(boolean typeEquality, boolean ownerEquality){
        if(typeEquality && ownerEquality) return HeadComparisonResult.SAME_TYPE_AND_OWNER;
        if(typeEquality) return HeadComparisonResult.SAME_TYPE;
        if(ownerEquality) return HeadComparisonResult.SAME_OWNER;
        return NO_SIMILARITY;
    }
}
