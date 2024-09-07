package com.github.crashdemons.playerheads.compatibility.craftbukkit;
import com.github.crashdemons.playerheads.compatibility.CompatibleProfile;
import org.bukkit.Bukkit;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompatibleProfilePP extends CompatibleProfile {

    //private PlayerProfile pp = null;
    private static final String SkinTagTemplate = "{\"textures\":{\"SKIN\":{\"url\":\"%URL%\"}}}";
    private static final Pattern SkinURLPattern = Pattern.compile("\"SKIN\":\\{\"url\":\"([^\"]+)\"");



    public CompatibleProfilePP(){
        super();
    }
    public CompatibleProfilePP(@NotNull UUID id, @NotNull String name){
        super(id,name);
    }
    public CompatibleProfilePP(@NotNull UUID id, @Nullable String name, boolean usernameCompatibilityMode){
        super(id,name,usernameCompatibilityMode);
    }
    public CompatibleProfilePP(@Nullable UUID id, @Nullable String name, boolean usernameCompatibilityMode, boolean idCompatibilityMode){
        super(id,name,usernameCompatibilityMode,idCompatibilityMode);
    }

    public CompatibleProfilePP(Object internalProfile){
        super();
        if(internalProfile==null) throw new IllegalArgumentException("Constructing from null Profile");
        setFromInternalObject(internalProfile);
    }

    public URL getSkinURL(){
        if(textures==null) return null;
        String sURL = getURLFromB64TextureValue(textures);
        if(sURL==null) return null;
        try{ return new URL(sURL); }catch (Exception ex){ return null; }
    }
    public static String getURLFromSkinTag(String tag){
        //System.out.println("Skinpattern: "+"\"SKIN\":\\{\"url\":\"([^\"]+)\"");
        //System.out.println("Skintag: "+tag);
        Matcher matcher = SkinURLPattern.matcher(tag);
        if(!matcher.find()){
            //System.out.println("Does not match");
            return null;
        }
        return matcher.group(1);
    }
    public static String getURLFromB64TextureValue(String tvalue){
        Base64.Decoder dec = Base64.getDecoder();
        byte[] bytes = dec.decode(tvalue);
        String skinTag = new String(bytes);
        //System.out.println("Skintag: "+skinTag);
        return getURLFromSkinTag(skinTag);
    }
    public static String createSkinTag(URL skinURL){
        if(skinURL==null) return null;
        return SkinTagTemplate.replace("%URL%",skinURL.toString());
    }
    public static String createB64TextureValue(URL skinURL){
        if(skinURL==null) return null;
        Base64.Encoder enc = Base64.getEncoder();
        String tag = createSkinTag(skinURL);
        return enc.encodeToString(tag.getBytes());
    }
    public static String createB64TextureValue(PlayerTextures tx){
        if(tx==null) return null;
        return createB64TextureValue(tx.getSkin());
    }


    @Override
    public void setFromInternalObject(Object internalProfile) throws IllegalArgumentException {
        if(internalProfile==null) return;
        if(!(internalProfile instanceof PlayerProfile _pp)) throw new IllegalArgumentException("object must be a PlayerProfile");
        //pp=_pp;
        this.id = _pp.getUniqueId();
        this.name = _pp.getName();
        this.textures=createB64TextureValue(_pp.getTextures());
    }

    @Override
    public Object toInternalObject() {
        //if(pp!=null) return pp;
        if(this.id==null || this.name==null) return null;
        PlayerProfile _pp = Bukkit.getServer().createPlayerProfile(this.id,this.name);
        PlayerTextures _pt = _pp.getTextures();//supposedly not null ever...
        _pt.setSkin(getSkinURL());
        return _pp;

       // _pt.setSkin(this.);
       // _pp.setTextures();
        //return null;
    }
}
