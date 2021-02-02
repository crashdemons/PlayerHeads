/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.modern;

import com.github.crashdemons.playerheads.compatibility.common.SkullDetails_common;
import com.github.crashdemons.playerheads.compatibility.RuntimeReferences;
import com.github.crashdemons.playerheads.compatibility.SkullBlockAttachment;
import com.github.crashdemons.playerheads.compatibility.SkullDetails;
import com.github.crashdemons.playerheads.compatibility.SkullType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.inventory.ItemStack;

/**
 * SkullDetails implementation for 1.13+ support
 * @author crashdemons (crashenator at gmail.com)
 */
public class SkullDetails_modern extends SkullDetails_common implements SkullDetails{
    Material material;
    Material materialWall;
    //private final SkullType skullType;
    public SkullDetails_modern(SkullType type){
        if(type==null){
            type=SkullType.PLAYER;
            material=Material.PLAYER_HEAD;
            materialWall=Material.PLAYER_WALL_HEAD;
        }
        
        String typeName=type.name();
        String suffix=type.isSkull? "_SKULL" : "_HEAD";
        material=RuntimeReferences.getMaterialByName(typeName+suffix);
        materialWall=RuntimeReferences.getMaterialByName(typeName+"_WALL"+suffix);
        if(material==null){
            type=SkullType.PLAYER;
            material=Material.PLAYER_HEAD;
            materialWall=Material.PLAYER_WALL_HEAD;
        }
        this.skullType=type;
        this.materialItem=material;
        
        
    }
    

    
    /**
     * see https://bukkit.org/threads/set-block-direction-easy.474786/
     * @author The_Spaceman
     */
    private static Axis convertBlockFaceToAxis(BlockFace face) {
        switch (face) {
            case NORTH:
            case SOUTH:
                return Axis.Z;
            case EAST:
            case WEST:
                return Axis.X;
            case UP:
            case DOWN:
                return Axis.Y;
                default:
                    return Axis.X;
        }
    }
    /**
     * see https://bukkit.org/threads/set-block-direction-easy.474786/
     * @author The_Spaceman
     */
    protected static void setBlockRotation(Block block, BlockFace blockFace) {
        BlockState state = block.getState();
        BlockData blockData = state.getBlockData();
        if (blockData instanceof Directional) {
            System.out.println("Directional : "+blockFace);//TODO: DEBUG
            ((Directional) blockData).setFacing(blockFace);
        }
        if (blockData instanceof Orientable) {
            System.out.println("Orientable : "+blockFace);//TODO: DEBUG
            ((Orientable) blockData).setAxis(convertBlockFaceToAxis(blockFace));
        }
        if (blockData instanceof Rotatable) {
            System.out.println("Rotable : "+blockFace);//TODO: DEBUG
            ((Rotatable) blockData).setRotation(blockFace);
        }
        state.setBlockData(blockData);
        state.update(false,true);
    }
    
    
    
    @Override public boolean isVariant(){ return false; }
    @Override public boolean isBackedByPlayerhead(){ return material==Material.PLAYER_HEAD; }
    //@Override public boolean isSkinnable(){ return isBackedByPlayerhead(); }
    @Override public ItemStack createItemStack(int quantity){ return new ItemStack(material,quantity); }
    //@Override public Material getItemMaterial(){ return material; }
    @Override public Material getFloorMaterial(){ return material; }
    @Override public Material getWallMaterial(){ return materialWall; }
    
    @Override
    protected void setBlockDetails(Block b, BlockFace rotation, SkullBlockAttachment attachment){
        setBlockRotation(b, rotation);
    }
    
    
    @Override
    public Material getBlockMaterial(SkullBlockAttachment attachment){
        switch (attachment){
            case FLOOR:
                return getFloorMaterial();
            case WALL:
                return getWallMaterial();
            default:
                return getFloorMaterial();
        }
    }
}
