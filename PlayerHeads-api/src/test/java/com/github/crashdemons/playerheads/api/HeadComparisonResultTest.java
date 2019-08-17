/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class HeadComparisonResultTest {
    
    public HeadComparisonResultTest() {
    }

    @Test
    public void testIsSameOwnerTO() {
        System.out.println("isSameOwnerTO");
        HeadComparisonResult instance = HeadComparisonResult.SAME_TYPE_AND_OWNER;
        boolean expResult = true;
        boolean result = instance.isSameOwner();
        assertEquals(expResult, result);
    }

    @Test
    public void testIsSameOwnerT() {
        System.out.println("isSameOwnerT");
        HeadComparisonResult instance = HeadComparisonResult.SAME_TYPE;
        boolean expResult = false;
        boolean result = instance.isSameOwner();
        assertEquals(expResult, result);
    }

    @Test
    public void testIsSameOwnerO() {
        System.out.println("isSameOwnerO");
        HeadComparisonResult instance = HeadComparisonResult.SAME_OWNER;
        boolean expResult = true;
        boolean result = instance.isSameOwner();
        assertEquals(expResult, result);
    }
    @Test
    public void testIsSameOwnerN() {
        System.out.println("isSameOwnerN");
        HeadComparisonResult instance = HeadComparisonResult.NO_SIMILARITY;
        boolean expResult = false;
        boolean result = instance.isSameOwner();
        assertEquals(expResult, result);
    }

    @Test
    public void testIsSameTypeTO() {
        System.out.println("isSameTypeTO");
        HeadComparisonResult instance = HeadComparisonResult.SAME_TYPE_AND_OWNER;
        boolean expResult = true;
        boolean result = instance.isSameType();
        assertEquals(expResult, result);
    }
    @Test
    public void testIsSameTypeT() {
        System.out.println("isSameTypeT");
        HeadComparisonResult instance = HeadComparisonResult.SAME_TYPE;
        boolean expResult = true;
        boolean result = instance.isSameType();
        assertEquals(expResult, result);
    }
    @Test
    public void testIsSameTypeO() {
        System.out.println("isSameTypeO");
        HeadComparisonResult instance = HeadComparisonResult.SAME_OWNER;
        boolean expResult = false;
        boolean result = instance.isSameType();
        assertEquals(expResult, result);
    }
    @Test
    public void testIsSameTypeN() {
        System.out.println("isSameTypeN");
        HeadComparisonResult instance = HeadComparisonResult.NO_SIMILARITY;
        boolean expResult = false;
        boolean result = instance.isSameType();
        assertEquals(expResult, result);
    }

    @Test
    public void testIsSameTO() {
        System.out.println("isSameTO");
        HeadComparisonResult instance = HeadComparisonResult.SAME_TYPE_AND_OWNER;
        boolean expResult = true;
        boolean result = instance.isSame();
        assertEquals(expResult, result);
    }
    @Test
    public void testIsSameT() {
        System.out.println("isSameT");
        HeadComparisonResult instance = HeadComparisonResult.SAME_TYPE;
        boolean expResult = false;
        boolean result = instance.isSame();
        assertEquals(expResult, result);
    }
    @Test
    public void testIsSameO() {
        System.out.println("isSameO");
        HeadComparisonResult instance = HeadComparisonResult.SAME_OWNER;
        boolean expResult = false;
        boolean result = instance.isSame();
        assertEquals(expResult, result);
    }
    @Test
    public void testIsSameN() {
        System.out.println("isSameN");
        HeadComparisonResult instance = HeadComparisonResult.NO_SIMILARITY;
        boolean expResult = false;
        boolean result = instance.isSame();
        assertEquals(expResult, result);
    }

    @Test
    public void testAdd() {
        System.out.println("add");
        for(HeadComparisonResult result : HeadComparisonResult.values()){
            assertEquals(HeadComparisonResult.SAME_TYPE_AND_OWNER,HeadComparisonResult.SAME_TYPE_AND_OWNER.add(result));
            assertEquals(HeadComparisonResult.SAME_TYPE_AND_OWNER,result.add(HeadComparisonResult.SAME_TYPE_AND_OWNER));
            
            assertEquals(result,result.add(result));
            
            
            assertEquals(result,HeadComparisonResult.NO_SIMILARITY.add(result));
            assertEquals(result,result.add(HeadComparisonResult.NO_SIMILARITY));
            
            
        }
        assertEquals(HeadComparisonResult.SAME_TYPE_AND_OWNER,HeadComparisonResult.SAME_TYPE.add(HeadComparisonResult.SAME_OWNER));
        assertEquals(HeadComparisonResult.SAME_TYPE_AND_OWNER,HeadComparisonResult.SAME_OWNER.add(HeadComparisonResult.SAME_TYPE));
    }

    @Test
    public void testFromEquality() {
        System.out.println("fromEquality");
        for(int i=0;i<=1;i++){
            for(int j=0;j<=1;j++){
                boolean typeEquality = (i==1);
                boolean ownerEquality = (j==1);
                HeadComparisonResult result = HeadComparisonResult.fromEquality(typeEquality, ownerEquality);
                assertEquals(typeEquality, result.isSameType());
                assertEquals(ownerEquality, result.isSameOwner());
                assertEquals(typeEquality && ownerEquality, result.isSame());
            }
        }
    }
    
}
