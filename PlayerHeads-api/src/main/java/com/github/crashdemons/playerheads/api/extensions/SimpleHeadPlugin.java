/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api.extensions;

import java.util.ArrayList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Abstract class for creating a bukkit plugin supplying custom heads directly.
 * Note: using this makes your dependence on PlayerHeads non-optional, consider using HeadExtensionBase if this is not desirable.
 * You should not use this class if you want to have a separate plugin class.
 * @author crashdemons (crashenator at gmail.com)
 * @deprecated Draft API - not implemented yet.
 */
@Deprecated
public abstract class SimpleHeadPlugin extends JavaPlugin implements HeadExtension {
    
    private final boolean REGISTER_EXT;
    private final boolean REGISTER_HEADS;
    private final ArrayList<CustomHead> HEADS;
    
    /**
     * Constructs a plugin that adds custom heads to PlayerHeads
     * @param registerExtension controls whether the Head Extension will automatically be registered with PlayerHeads on-enable
     * @param registerHeads controls whether the defined heads will automatically be registered with PlayerHeads on-enable
     */
    public SimpleHeadPlugin(final boolean registerExtension, final boolean registerHeads){
        super();
        REGISTER_EXT = registerExtension;
        REGISTER_HEADS = registerHeads;
        HEADS = new ArrayList<>();
    }
    
    /**
     * Constructs a plugin that adds custom heads to PlayerHeads.
     * This constructor ensures the the extension and heads will automatically be registered on-enable
     */
    public SimpleHeadPlugin(){
        this(true,true);
    }
    //----------------------------------------
    
    /**
     * Sets the list of custom heads that will be registered by registerHeads() or on-enable.
     * @param heads the list of heads associated with this extension
     */
    public void setHeads(final ArrayList<CustomHead> heads){
        synchronized(HEADS){
            HEADS.clear();
            HEADS.addAll(heads);
        }
    }
    
    //---------------------------------------
    
    
    /**
     * Method called by Bukkit when the plugin is loaded - also adds PlayerHeads head registration features unless overridden.
     */
    @Override
    public void onEnable(){
        if(REGISTER_EXT) register();
        if(REGISTER_HEADS) registerHeads();
    }
    
    //----------------------------------------
    
    /**
     * Registers the heads defined previously for the plugin with PlayerHeads.
     * This method is called by the onLoad method by default.
     * Heads should be defined by the setHeads() method before onEnable is called.
     * @see #setHeads(java.util.ArrayList) 
     */
    public void registerHeads(){
        synchronized(HEADS){
            for(CustomHead chr : HEADS){
                registerHead(chr);
            }
        }
    }
    
    /**
     * Registers an individual head with PlayerHeads
     * @param hr the head to register
     * @return whether the head could be registered
     */
    boolean registerHead(final CustomHead hr){
        return HeadExtensionManager.registerHeadRepresentation(this, hr);
    }
    
    /**
     * Registers the extension with PlayerHeads.
     * If the extension is also an "identifier" or "updater", this method will register it that way as well.
     * This method is called by the onLoad method by default.
     */
    void register(){
        HeadExtensionManager.registerExtension(this, this);
        if(this instanceof HeadIdentifier) HeadExtensionManager.registerIdentifier(this, (HeadIdentifier) this);
        if(this instanceof HeadUpdater) HeadExtensionManager.registerUpdater(this, (HeadUpdater) this);
    }
    
    
}
