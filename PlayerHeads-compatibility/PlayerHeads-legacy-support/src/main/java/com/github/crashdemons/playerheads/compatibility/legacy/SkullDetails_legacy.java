/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.legacy;

import com.github.crashdemons.playerheads.compatibility.SkullBlockAttachment;
import com.github.crashdemons.playerheads.compatibility.SkullDetails;
import com.github.crashdemons.playerheads.compatibility.SkullType;
import com.github.crashdemons.playerheads.compatibility.Version;
import com.github.crashdemons.playerheads.compatibility.common.SkullDetails_common;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;

/**
 * SkullDetails implementation for 1.8+ support
 * @author crashdemons (crashenator at gmail.com)
 */

public class SkullDetails_legacy extends SkullDetails_common implements SkullDetails {
    //public final Material materialItem;
    public final Material materialBlock;
    public final short datavalue;
    //private final SkullType skullType;
    
    
    private enum LegacyHeadData{
        FLOOR(0x1,true),// On the floor (rotation is stored in the tile entity)
        WALL_NORTH(0x2,false),// On a wall, facing north
        WALL_SOUTH(0x3,false),// On a wall, facing south
        WALL_EAST(0x4,false),// On a wall, facing east
        WALL_WEST(0x5,false)// On a wall, facing west
        ;
        public final byte value;
        public final boolean needsTileData;
        private LegacyHeadData(int b, boolean needsMoreData){
            value = (byte) b;
            needsTileData = needsMoreData;
        }
        
        public static LegacyHeadData get(BlockFace rotation, SkullBlockAttachment attachment){
            if(attachment==SkullBlockAttachment.FLOOR) return LegacyHeadData.FLOOR;
            try{
                return LegacyHeadData.valueOf("WALL_"+rotation.name().toUpperCase());
            }catch(Exception e){
                return null;
            }
        }
    }
    
    public SkullDetails_legacy(SkullType skullType){
        materialBlock=Material.SKULL;
        materialItem=Material.SKULL_ITEM;
        if( skullType==null || (skullType==SkullType.DRAGON && Version.checkUnder(1, 9)) ){
            this.skullType=SkullType.PLAYER;
            datavalue=(short)SkullType.PLAYER.ordinal();
        }else{
            this.skullType=skullType;
            datavalue=(short) skullType.ordinal();
        }
    }
    
    @Override public boolean isVariant(){ return true; }//always SKULL_ITEM
    @Override public boolean isBackedByPlayerhead(){ return skullType==SkullType.PLAYER; }
    //@Override public boolean isSkinnable(){ return isBackedByPlayerhead(); }
    @Override public ItemStack createItemStack(int quantity){ return new ItemStack(materialItem,quantity,datavalue); }
    //@Override public Material getItemMaterial(){ return materialItem; }
    @Override public Material getFloorMaterial(){ return materialBlock; }
    @Override public Material getWallMaterial(){ return materialBlock; }
    
    
    @Override
    protected void setBlockDetails(Block b, BlockFace rotation, SkullBlockAttachment attachment){
        BlockState state = b.getState();
        LegacyHeadData legacyData = LegacyHeadData.get(rotation, attachment);
        if(legacyData!=null){
            b.setData(legacyData.value, true);
            System.out.println("LegacyData set on block "+rotation+" "+attachment);//TODO: DEBUG
            if(legacyData.needsTileData){
                MaterialData matData = state.getData();
                if(matData instanceof Directional){
                    Directional skullMatData = (Directional) matData;
                    skullMatData.setFacingDirection(rotation);
                    state.setData(matData);//probably not necessary but might as well
                    System.out.println("MaterialData set to rotation "+rotation);//TODO: DEBUG
                }else{
                    System.out.println("MaterialData was not directional :( ");//TODO: DEBUG
                }
            }else{
                System.out.println("LegacyData did not need additional tile data");//TODO: DEBUG
            }
        }else{
            System.out.println("LegacyData was null - "+rotation+" "+attachment);//TODO: DEBUG
        }
        state.update();
    }
    
    
    @Override
    public Material getBlockMaterial(SkullBlockAttachment attachment){
        return materialBlock;
    }
    
    
}
