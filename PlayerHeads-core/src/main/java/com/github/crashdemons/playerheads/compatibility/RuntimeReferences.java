/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility;

import java.lang.reflect.Method;
import org.bukkit.Material;

/**
 * Provides methods to perform runtime lookups of values by name (eg: enums)
 * @author crashdemons (crashenator at gmail.com)
 */
public final class RuntimeReferences {
    private RuntimeReferences(){}
    public static BackwardsCompatibleSkullType getCompatibleMaterialByName(String name){
        try{
            return BackwardsCompatibleSkullType.valueOf(name);
        }catch(IllegalArgumentException e){
            return null;
        }
    }
    public static Material getMaterialByName(String name){
        try{
            return Material.valueOf(name);
        }catch(IllegalArgumentException e){
            return null;
        }
    }
    public static SkullType getSkullTypeByName(String name){
        try{
            return SkullType.valueOf(name);
        }catch(IllegalArgumentException e){
            return null;
        }
    }
    public static Class<?> getClass(String classname){
        try {
            return Class.forName(classname);
        } catch (Exception e) {
            return null;
        }
    }
    public static Method getMethod(Class<?> classobj, String methodname, Class<?>... parameterTypes){
        try{
            return classobj.getMethod(methodname, parameterTypes);
        }catch(Exception e){
            return null;
        }
    }
    public static Method getMethod(String classname, String methodname,Class<?>... parameterTypes){
        Class<?> classobj = getClass(classname);
        if(classobj==null) return null;
        return getMethod(classobj,methodname,parameterTypes);
    }
    public static boolean hasClass(String classname){
        try {
            Class<?> providerClass = Class.forName(classname);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
