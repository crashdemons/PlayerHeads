/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility;

import org.bukkit.Bukkit;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
class CompatibilityUtils {
    public static String getMCVersion(){
        String ver = Bukkit.getVersion();
        int pos = ver.indexOf("MC: ");
        if(pos==-1) return "";
        String ver_mc_untrimmed=ver.substring(pos+"MC: ".length());
        int pos_paren=ver_mc_untrimmed.indexOf(")");
        if(pos_paren==-1) return "";
        return ver_mc_untrimmed.substring(0, pos_paren);
    }
    public static int[] getMCVersionParts(){
        String ver = CompatibilityUtils.getMCVersion();
        if(ver.isEmpty()) return null;
        String[] parts = (ver+".0.0").split("\\.");
        try{
            return new int[]{Integer.parseInt(parts[0]),Integer.parseInt(parts[1])};
        }catch(NumberFormatException e){
            return null;
        }
    }
    
}
