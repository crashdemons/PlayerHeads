/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.util;

import org.bukkit.entity.EntityType;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class Main {
    public static void generateLangDefinitions(){
        System.out.println("===========================================");
        System.out.println("Generating Lang Definitions:");
        System.out.println("===========================================");
        for(EntityType type : EntityType.values()){
            if(!type.isAlive()) continue;
            if(type==EntityType.ARMOR_STAND || type==EntityType.PLAYER || type==EntityType.GIANT) continue;
            String name=type.name();
            String spaced = type.name().replace("_", " ").toLowerCase();
            String spawn=type.name().replace("_", "").toLowerCase();
            String caps = camelCase(spaced);
            System.out.println("public static String HEAD_"+name+";");//+"="+caps+" Head");
            System.out.println("public static String HEAD_SPAWN_"+name+";");//=#"+spawn);
        }
        System.out.println("===========================================");
    }
    
    public static void generateLangEntries(){
        System.out.println("===========================================");
        System.out.println("Generating Lang Entries: (needs manual editing for skulls)");
        System.out.println("===========================================");
        for(EntityType type : EntityType.values()){
            if(!type.isAlive()) continue;
            if(type==EntityType.ARMOR_STAND || type==EntityType.PLAYER || type==EntityType.GIANT) continue;
            String name=type.name();
            String spaced = type.name().replace("_", " ").toLowerCase();
            String spawn=type.name().replace("_", "").toLowerCase();
            String caps = camelCase(spaced);
            System.out.println("HEAD_"+name+"="+caps+" Head");
            System.out.println("HEAD_SPAWN_"+name+"=#"+spawn);
        }
        System.out.println("===========================================");
    }
    
    public static void generateSpawnDocumentation(){
        System.out.println("===========================================");
        System.out.println("Generating Spawn Entries: (needs manual editing for skulls)");
        System.out.println("===========================================");
        for(EntityType type : EntityType.values()){
            if(!type.isAlive()) continue;
            if(type==EntityType.ARMOR_STAND || type==EntityType.PLAYER || type==EntityType.GIANT) continue;
            String name=type.name();
            String spaced = type.name().replace("_", " ").toLowerCase();
            String spawn=type.name().replace("_", "").toLowerCase();
            String caps = camelCase(spaced);
            System.out.println("<tr><td>"+caps+"</td><td>#"+spawn+"</td></tr>");
        }
        System.out.println("===========================================");
    }
    
    public static void main(String [ ] args){
        generateLangDefinitions();
        generateLangEntries();
        generateSpawnDocumentation();
    }
    
            
    private static String camelCase(String str)
    {
        StringBuilder builder = new StringBuilder(str);
        // Flag to keep track if last visited character is a 
        // white space or not
        boolean isLastSpace = true;

        // Iterate String from beginning to end.
        for(int i = 0; i < builder.length(); i++)
        {
                char ch = builder.charAt(i);

                if(isLastSpace && ch >= 'a' && ch <='z')
                {
                        // Character need to be converted to uppercase
                        builder.setCharAt(i, (char)(ch + ('A' - 'a') ));
                        isLastSpace = false;
                }else if (ch != ' ')
                        isLastSpace = false;
                else
                        isLastSpace = true;
        }

        return builder.toString();
    } 
    
}
