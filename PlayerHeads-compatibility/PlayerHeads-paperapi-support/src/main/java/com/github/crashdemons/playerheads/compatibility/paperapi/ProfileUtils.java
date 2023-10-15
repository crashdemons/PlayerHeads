/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.paperapi;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.github.crashdemons.playerheads.compatibility.CompatibleProfile;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import org.bukkit.block.Skull;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

/**
 * Provides methods for working with profiles on items and blocks.
 * Credit goes to x7aSv for basic implementation of custom head creation.
 * @author crashdemons (crashenator at gmail.com)
 * @deprecated This class is server implementation-specific and should not be used directly if possible, the namespace may change in the future.
 */
@Deprecated
public class ProfileUtils {
    
    //internal methods - can be typed
    
    private static Field getProfileField(Object skull) throws IllegalArgumentException,NoSuchFieldException,SecurityException,IllegalAccessException{
        if(!(skull instanceof SkullMeta || skull instanceof Skull)) throw new IllegalArgumentException("Object is not a supported type: SkullMeta or Skull (blockstate)");
        Field profileField = skull.getClass().getDeclaredField("profile");
        profileField.setAccessible(true);
        return profileField;
    }
    private static Method getPAProfileMethod(Object skull) throws IllegalArgumentException,NoSuchMethodException,SecurityException,IllegalAccessException{
        if(!(skull instanceof SkullMeta || skull instanceof Skull)) throw new IllegalArgumentException("Object is not a supported type: SkullMeta or Skull (blockstate)");
        Method profileMethod = skull.getClass().getDeclaredMethod("setProfile", PlayerProfile.class);
        profileMethod.setAccessible(true);
        return profileMethod;
    }
    
    //-------------------------------------------------------------------------
    
    public static PlayerProfile getInternalProfile(Object skull) throws IllegalStateException{
        try {
            return (PlayerProfile) getProfileField(skull).get(skull);
        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
            throw new IllegalStateException("The profile field value could not be retrieved");
        }
    }
    public static CompatibleProfile getProfile(Object skull) throws IllegalStateException{
        PlayerProfile profile = getInternalProfile(skull);
        if(profile==null) return new CompatibleProfilePA();
        return new CompatibleProfilePA(profile);
    }
    
    private static boolean setInternalProfilePA(Object skull, PlayerProfile profile) throws IllegalStateException{
        try {
            getPAProfileMethod(skull).invoke(skull, profile);
            return true;
        } catch (NoSuchMethodException | InvocationTargetException | SecurityException | IllegalAccessException error) {
            return false;
        }
    }
    
    public static boolean setInternalProfile(Object skull, PlayerProfile profile) throws IllegalStateException{
        try {
            /* in some version of Spigot after 1.8, a method was added to set the profile, which also sets serialization data
            we shouuld prefer using the craftbukkit method over directly accessing the profile field in order to serialize correctly.
            */
            if(setInternalProfilePA(skull,profile)) return true; 
            getProfileField(skull).set(skull, profile);
            return true;
        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
            throw new IllegalStateException("The profile field value could not be retrieved");
        }
    }
    public static boolean setProfile(Object skull, CompatibleProfile profile) throws IllegalStateException{
        return setInternalProfile(skull, (PlayerProfile) profile.toInternalObject());
    }
    
    
    //-------------------------------------------------------------------------
    
    /**
     * Set a profile field in the supplied item meta using a UUID, name, and Texture string
     * @param skull the item/block skull object to apply the profile on
     * @param uuid A UUID to be associated with this profile and texture
     * @param username A username (or a unique plugin identifier) to be associated with this profile.
     * @param texture The Base64-encoded Texture-URL tags.
     * @return True: the profile was successfully set. False: the profile could not be set.
     */
    public static boolean setProfile(Object skull, @NotNull UUID uuid, @NotNull String username, String texture) throws IllegalStateException{//credit to x7aSv for original
        CompatibleProfile profile = new CompatibleProfilePA(uuid,null);
        profile.setTextures(texture);
        return setProfile(skull, profile);
    }
    
    /**
     * Set a profile field in the supplied block state using a UUID, name, and Texture string
     * @param headBlockState the block state to apply the profile on
     * @param uuid A UUID to be associated with this profile and texture
     * @param username A username (or a unique plugin identifier) to be associated with this profile.
     * @param texture The Base64-encoded Texture-URL tags.
     * @return True: the profile was successfully set. False: the profile could not be set.
     */
    public static boolean setProfile(Skull headBlockState, UUID uuid, @NotNull String username, String texture) throws IllegalStateException{
        return setProfile((Object)headBlockState, uuid, username, texture);
    }
    
    public static boolean clearProfile(Object skull){
        if(!(skull instanceof SkullMeta || skull instanceof Skull)) throw new IllegalArgumentException("Object is not a supported type: SkullMeta or Skull (blockstate)");
        try{
            return setInternalProfile(skull, null);
        }catch(Exception e){
            return false;
        }
    }
    
}
