/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.craftbukkit;

import com.github.crashdemons.playerheads.compatibility.CompatibilityProvider;
import com.github.crashdemons.playerheads.compatibility.Version;
import com.github.crashdemons.playerheads.compatibility.spigot_1_21.Provider;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class CompatibleProfilePPTest {

    public CompatibleProfilePPTest() {
    }


    static final String TextureURL = "http://textures.minecraft.net/texture/cd6e602f76f80c0657b5aed64e267eeea702b31e6dae86346c8506f2535ced02";
    static final String TextureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q2ZTYwMmY3NmY4MGMwNjU3YjVhZWQ2NGUyNjdlZWVhNzAyYjMxZTZkYWU4NjM0NmM4NTA2ZjI1MzVjZWQwMiJ9fX0=";
    static final String SkinTag = "{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/cd6e602f76f80c0657b5aed64e267eeea702b31e6dae86346c8506f2535ced02\"}}}";
    
    
    @Test
    public void testDecodeTextureValue(){
        System.out.println("testDecodeTextureValue");
        String url = CompatibleProfilePP.getURLFromB64TextureValue(TextureValue);
        assertEquals(TextureURL,url);
    }
    @Test
    public void testEncodeTextureValue() throws MalformedURLException {
        System.out.println("testEncodeTextureValue");
        String tx = CompatibleProfilePP.createB64TextureValue(new URL(TextureURL));
        assertEquals(TextureValue,tx);
    }
    @Test
    public void testCreateSkinTag() throws MalformedURLException {
        System.out.println("testEncodeTextureValue2");
        String tag = CompatibleProfilePP.createSkinTag(new URL(TextureURL));
        assertEquals(SkinTag,tag);
    }
    
}
