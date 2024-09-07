/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.craftbukkit;

import com.github.crashdemons.playerheads.compatibility.CompatibleProfile;
import org.bukkit.block.Skull;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Provides methods for working with profiles on items and blocks.
 * Credit goes to x7aSv for basic implementation of custom head creation.
 * @author crashdemons (crashenator at gmail.com)
 * @deprecated This class is server implementation-specific and should not be used directly if possible, the namespace may change in the future.
 */
@Deprecated
public class ProfileUtils {

    //internal methods - can be typed


    
    //-------------------------------------------------------------------------
    
    public static PlayerProfile getInternalProfile(Object skull) throws IllegalStateException{
        try {
            if(skull instanceof SkullMeta sm){
                return sm.getOwnerProfile();
            }
            if(skull instanceof Skull sb){
                return sb.getOwnerProfile();
            }
            return null;
        } catch (Exception error) {
            throw new IllegalStateException("The profile field value could not be retrieved", error);
        }
    }
    public static CompatibleProfile getProfile(Object skull) throws IllegalStateException{
        PlayerProfile profile = getInternalProfile(skull);
        if(profile==null) return new CompatibleProfilePP();
        return new CompatibleProfilePP(profile);
    }
    
    
    private static boolean setInternalProfileCB(Object skull, PlayerProfile profile) throws IllegalStateException{
        try {
            if(skull instanceof SkullMeta sm){
                sm.setOwnerProfile(profile);
                return true;
            }
            if(skull instanceof Skull sb){
                sb.setOwnerProfile(profile);
                return true;
            }
            return false;
        } catch (Exception error) {
            return false;
        }
    }
    
    public static boolean setInternalProfile(Object skull, PlayerProfile profile) throws IllegalStateException{
        try {
            /* in some version of Spigot after 1.8, a method was added to set the profile, which also sets serialization data
            we shouuld prefer using the craftbukkit method over directly accessing the profile field in order to serialize correctly.
            */
            return setInternalProfileCB(skull,profile);
        } catch (Exception error) {
            throw new IllegalStateException("The profile field value could not be retrieved/set", error);
        }
    }
    public static boolean setProfile(Object skull, CompatibleProfile profile) throws IllegalStateException{
        return setInternalProfile(skull, (PlayerProfile) profile.toInternalObject());
    }
    
    
    //-------------------------------------------------------------------------
    
    /**
     * Set a profile field in the supplied item meta using a UUID, name and Texture string
     * @param skull the item/block skull object to apply the profile on
     * @param uuid A UUID to be associated with this profile and texture
     * @param username A username (or a unique plugin identifier) to be associated with this profile.
     * @param texture The Base64-encoded Texture-URL tags.
     * @return True: the profile was successfully set. False: the profile could not be set.
     */
    public static boolean setProfile(Object skull, @NotNull UUID uuid, @NotNull String username, String texture) throws IllegalStateException{//credit to x7aSv for original
        CompatibleProfile profile = new CompatibleProfilePP(uuid,username);
        profile.setTextures(texture);
        return setProfile(skull, profile);
    }
    
    /**
     * Set a profile field in the supplied block state using a UUID, username, and Texture string
     * @param headBlockState the block state to apply the profile on
     * @param uuid A UUID to be associated with this profile and texture
     * @param username A username (or a unique plugin identifier) to be associated with this profile.
     * @param texture The Base64-encoded Texture-URL tags.
     * @return True: the profile was successfully set. False: the profile could not be set.
     */
    public static boolean setProfile(Skull headBlockState, @NotNull UUID uuid, @NotNull String username, String texture) throws IllegalStateException{
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
