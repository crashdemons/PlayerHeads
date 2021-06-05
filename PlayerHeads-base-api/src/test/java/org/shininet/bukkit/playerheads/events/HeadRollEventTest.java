/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package org.shininet.bukkit.playerheads.events;

import java.util.Map;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.junit.Test;
import static org.junit.Assert.*;
import org.shininet.bukkit.playerheads.events.modifiers.DropRateModifier;
import org.shininet.bukkit.playerheads.events.modifiers.DropRateModifierType;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class HeadRollEventTest {
    
    public HeadRollEventTest() {
    }

    @Test
    public void testGetModifiers() {
        System.out.println("getModifiers");
        HeadRollEvent instance = new HeadRollEvent(null, null, false, 0.2, 0.75);
        Map<String, DropRateModifier> result = instance.getModifiers();
        assertEquals(0,result.size());
        
        instance = new HeadRollEvent(null, null, false, 0.2, 0.75);
        instance.setModifier("testModifier", new DropRateModifier(DropRateModifierType.MULTIPLY,0.035));
        result = instance.getModifiers();
        assertEquals(1,result.size());
        assertEquals((Double) 0.035, (Double) result.get("testModifier").getValue());
        
    }
    @Test
    public void testApplyDropRate() {
        System.out.println("applyDropRate");
        HeadRollEvent instance;
        instance = new HeadRollEvent(null, null, false, 0.2, 0);
        instance.applyDropRate();
        assertFalse(instance.getDropSuccess());
        instance = new HeadRollEvent(null, null, false, 0, 0);
        instance.applyDropRate();
        assertFalse(instance.getDropSuccess());
        instance = new HeadRollEvent(null, null, false, 0.20001, 0.2);
        instance.applyDropRate();
        assertFalse(instance.getDropSuccess());
        instance = new HeadRollEvent(null, null, false, 0.2, 0.2);
        instance.applyDropRate();
        assertFalse(instance.getDropSuccess());
        instance = new HeadRollEvent(null, null, false, 0.1999, 0.2);
        instance.applyDropRate();
        assertTrue(instance.getDropSuccess());
    }
    @Test
    public void testApplyDropRateABH() {
        System.out.println("applyDropRateABH");
        HeadRollEvent instance;
        instance = new HeadRollEvent(null, null, true, 0.2, 0);
        instance.applyDropRate();
        assertFalse(instance.getDropSuccess());
        instance = new HeadRollEvent(null, null, true, 0, 0);
        instance.applyDropRate();
        assertFalse(instance.getDropSuccess());
        instance = new HeadRollEvent(null, null, true, 0.20001, 0.2);
        instance.applyDropRate();
        assertTrue(instance.getDropSuccess());
        instance = new HeadRollEvent(null, null, true, 0.2, 0.2);
        instance.applyDropRate();
        assertTrue(instance.getDropSuccess());
        instance = new HeadRollEvent(null, null, true, 0.1999, 0.2);
        instance.applyDropRate();
        assertTrue(instance.getDropSuccess());
    }
    @Test
    public void testApplyModifiers() {
        System.out.println("applyModifiers");
        HeadRollEvent instance;
        instance = new HeadRollEvent(null, null, true, 0.2, 0.035);
        instance.applyModifiers();
        assertEquals(0.035, instance.getEffectiveDropRate(), 0.00000000001);
        instance = new HeadRollEvent(null, null, true, 0.2, 0.0);
        instance.applyModifiers();
        assertEquals(0, instance.getEffectiveDropRate(), 0.00000000001);
        
        System.out.println("---");
        instance = new HeadRollEvent(null, null, true, 0.2, 0.01);
        instance.setModifier("mod1", new DropRateModifier(DropRateModifierType.ADD_CONSTANT,0.07));
        instance.applyModifiers();
        assertEquals( (0.01+0.07), instance.getEffectiveDropRate(), 0.00000000001);
        System.out.println("");
        instance.setModifier("mod2", new DropRateModifier(DropRateModifierType.ADD_MULTIPLE,0.02));
        instance.applyModifiers();
        assertEquals( (0.01+0.07)*1.02, instance.getEffectiveDropRate(), 0.00000000001);
        System.out.println("");
        instance.setModifier("mod3", new DropRateModifier(DropRateModifierType.ADD_MULTIPLE_PER_LEVEL,0.03,2));
        instance.applyModifiers();
        assertEquals( (0.01+0.07)*1.02*(1+0.03*2), instance.getEffectiveDropRate(), 0.00000000001);
        System.out.println("   expected: "+ ((0.01+0.07)*1.02*(1+0.03*2))+"   was: "+instance.getEffectiveDropRate());
        System.out.println("");
        instance.setModifier("mod4", new DropRateModifier(DropRateModifierType.MULTIPLY,1.5));
        instance.applyModifiers();
        assertEquals( (0.01+0.07)*1.02*(1+0.03*2)*1.5, instance.getEffectiveDropRate(), 0.00000000001);
        System.out.println("---");
        instance.setModifier("mod4", new DropRateModifier(DropRateModifierType.MULTIPLY,1.6));
        instance.applyModifiers();
        assertEquals( (0.01+0.07)*1.02*(1+0.03*2)*1.6, instance.getEffectiveDropRate(), 0.00000000001);
        System.out.println("---");
        instance.setModifier("mod2", new DropRateModifier(DropRateModifierType.ADD_MULTIPLE,0.06));
        instance.applyModifiers();
        assertEquals( (0.01+0.07)*1.06*(1+0.03*2)*1.6, instance.getEffectiveDropRate(), 0.00000000001);
        System.out.println("---");
    }
}
