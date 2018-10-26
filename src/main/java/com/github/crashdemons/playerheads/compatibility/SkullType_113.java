/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
enum SkullType_113 {
    SKELETON(true), WITHER_SKELETON(true), ZOMBIE(false), PLAYER(false), CREEPER(false), DRAGON(false);
    
    public final boolean isSkull;//has "_SKULL" suffix
    
    SkullType_113(boolean skull){
        isSkull=skull;
    }
}
