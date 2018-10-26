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
public class CompatibilityChecker {
    
    private static int versionMajor = 0;
    private static int versionMinor = 0;
    private static boolean isInit=false;
    
    private CompatibilityChecker(){}
    
    public static boolean checkVersionAtLeast(int major, int minor){
        init();
        return (versionMajor>major) || (versionMajor==major && versionMinor>=minor);
    }
    public static boolean checkVersionUnder(int major, int minor){
        init();
        return (versionMajor<major) || (versionMajor==major && versionMinor<minor);
    }
    public static boolean checkVersionIs(int major, int minor){
        init();
        return (versionMajor==major && versionMinor==minor);
    }
    
    public static synchronized void init(){
        if(isInit) return;
        int[] mcver = CompatibilityUtils.getMCVersionParts();
        if(mcver==null) throw new IllegalStateException("The current Bukkit build did not supply a version string that could be understood.");
        versionMajor=mcver[0];
        versionMinor=mcver[1];
        if(versionMajor<1 || (versionMajor==1 && versionMinor<8)) throw new IllegalStateException("Server versions under 1.8 are not supported.");
        isInit=true;
    }
}
