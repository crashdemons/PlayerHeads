/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.craftbukkit;

import com.mojang.authlib.properties.Property;

import java.lang.reflect.Method;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class CompatiblePropertyCB {
    private final Property prop;
    private final Method nameMethod;
    private final Method valueMethod;
    public CompatiblePropertyCB(Property prop){
        this.prop = prop;
        nameMethod = this.getNameMethod();
        valueMethod = this.getValueMethod();
    }
    
    @Deprecated
    public Property getBackingObject(){
        return prop;
    }
    
    public Method getNameMethod(){
        Method m = null;
        try{ 
            m = prop.getClass().getMethod("getName");
        } catch(Exception e){}
        if(m!=null) return m;
        
        try{ 
            m = prop.getClass().getMethod("name");
        } catch(Exception e){
            System.err.println("Could not find name method for authlib property");
        }
        return m;
    }
    
    public Method getValueMethod(){
        Method m = null;
        try{ 
            m = prop.getClass().getMethod("getValue");
        } catch(Exception e){}
        if(m!=null) return m;
        
        try{ 
            m = prop.getClass().getMethod("value");
        } catch(Exception e){
            System.err.println("Could not find value method for authlib property");
        }
        return m;
    }
    
    public String getName(){
        try{
            return (String) this.nameMethod.invoke(prop);
        }catch(Exception ex){
            System.err.println("Error getting name of property: "+ex.getMessage());
            return null;
        }
    }
    public String getValue(){
        try{
            return (String) this.valueMethod.invoke(prop);
        }catch(Exception ex){
            System.err.println("Error getting value of property: "+ex.getMessage());
            return null;
        }
        
    }
}
