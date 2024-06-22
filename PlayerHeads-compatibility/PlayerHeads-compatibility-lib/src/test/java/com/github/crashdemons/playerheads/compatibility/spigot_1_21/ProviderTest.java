/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.spigot_1_21;

import com.github.crashdemons.playerheads.compatibility.CompatibilityProvider;
import com.github.crashdemons.playerheads.compatibility.Version;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class ProviderTest {
    
    public ProviderTest() {
    }

    
    
    
    @Test
    public void testSupportsEntityTag(){
        Version.setDetectedServerVersion("com/github/crashdemons/playerheads/compatibility/craftbukkit", 1, 21);
        System.out.println("testSupportsEntityTag");
        CompatibilityProvider provider = new Provider();
        assertEquals(true,provider.supportsEntityTagType(true));
        assertEquals(true,provider.supportsEntityTagType(false));
        
    }
    
}
