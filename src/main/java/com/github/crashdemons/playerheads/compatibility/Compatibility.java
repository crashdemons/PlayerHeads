/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility;

import com.github.crashdemons.playerheads.ProfileUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class Compatibility {
    private Compatibility(){}
    
    private static CompatibilityProvider provider=null;
    
    public static synchronized void init(){ CompatibilityChecker.init(); }
    
    public static void registerProvider(CompatibilityProvider obj){
        if(provider!=null) throw new IllegalStateException("This project has been misconfigured because it contains multiple compatibility-providers.");
        provider=obj;
    }
    public static CompatibilityProvider getProvider(){
        if(provider==null) throw new IllegalStateException("Requested compatibility provider before any were registered.");
        return provider;
    }
    
    
    public static OfflinePlayer getOwningPlayer(SkullMeta meta){ 
        if(CompatibilityChecker.checkVersionAtLeast(1, 13)){
            return getOwningPlayer(SkullMeta.class,meta);
        }else{
            return ProfileUtils.getProfilePlayer(meta);
        }
    }
    public static OfflinePlayer getOwningPlayer(Skull meta){ 
        if(CompatibilityChecker.checkVersionAtLeast(1, 13)){
            return getOwningPlayer(Skull.class,meta);
        }else{
            return ProfileUtils.getProfilePlayer(meta);
        }
    }
    
    public static boolean setOwningPlayer(SkullMeta meta,OfflinePlayer op){//for head itemmeta
        if(CompatibilityChecker.checkVersionAtLeast(1, 13)){
            return setOwningPlayer(SkullMeta.class, meta, op);
        }else{
            return meta.setOwner(op.getName());//not the best solution, but a solution.
        }
    }
    public static boolean setOwningPlayer(Skull meta,OfflinePlayer op){//for head blockstates
        if(CompatibilityChecker.checkVersionAtLeast(1, 13)){
            return setOwningPlayer(Skull.class, meta, op);
        }else{
            return meta.setOwner(op.getName());//not the best solution, but a solution.
        }
    }
    private static boolean setOwningPlayer(Class<?> classType, Object obj, OfflinePlayer op){
        try{
            Method method = classType.getDeclaredMethod("setOwningPlayer",OfflinePlayer.class);
            return (boolean) method.invoke(obj,op);
        }catch(Exception e){
            return false;
        }
    }
    private static OfflinePlayer getOwningPlayer(Class<?> classType, Object obj){
        try{
            Method method = classType.getDeclaredMethod("getOwningPlayer");
            return (OfflinePlayer) method.invoke(obj);
        }catch(Exception e){
            return null;
        }
    }
    
    public static ItemStack getItemInMainHand(Player player){
        if(player==null) return null;
        
        EntityEquipment equipment=player.getEquipment();
        if(equipment==null) return null;
        
        Class<?> c = EntityEquipment.class;
        try{
            if(CompatibilityChecker.checkVersionAtLeast(1, 9)){
                Method method = c.getDeclaredMethod("getItemInMainHand");
                return (ItemStack) method.invoke(equipment);

            }else{
                return equipment.getItemInHand();
            }
        }catch(SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e){
            System.err.println("Could not call hand method by reflection");
            e.printStackTrace();
            return null;
        }
    }
    public static void setItemInMainHand(Player player,ItemStack stack){
        if(player==null) return;
        
        EntityEquipment equipment=player.getEquipment();
        if(equipment==null) return;
        
        Class<?> c = EntityEquipment.class;
        try{
            if(CompatibilityChecker.checkVersionAtLeast(1, 9)){
                Method method = c.getDeclaredMethod("setItemInMainHand",ItemStack.class);
                method.invoke(equipment,stack);

            }else{
                equipment.setItemInHand(stack);
            }
        }catch(SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e){
            System.err.println("Could not call hand method by reflection");
            e.printStackTrace();
            return;
        }
    }
}
