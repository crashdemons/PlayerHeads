/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api.extensions;

import org.bukkit.plugin.Plugin;

/**
 * Abstract class for creating custom head extension classes separate from your plugin class.
 * @author crashdemons (crashenator at gmail.com)
 * @deprecated Draft API - not implemented yet.
 */
@Deprecated
public abstract class SimpleHeadModule implements HeadExtension {
    
    /**
     * Register preset heads added by your extension that should replace heads with a matching UUID.
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
    void register(Plugin parentPlugin){
        HeadExtensionManager.registerExtension(parentPlugin, this);
        if(this instanceof HeadIdentifier) HeadExtensionManager.registerIdentifier(parentPlugin, (HeadIdentifier) this);
        if(this instanceof HeadUpdater) HeadExtensionManager.registerUpdater(parentPlugin, (HeadUpdater) this);
    }
    
    
}
