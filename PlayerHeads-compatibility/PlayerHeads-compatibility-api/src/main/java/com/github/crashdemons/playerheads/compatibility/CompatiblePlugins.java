/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility;

import com.github.crashdemons.playerheads.compatibility.plugins.HeadPluginCompatibility;
import com.github.crashdemons.playerheads.compatibility.plugins.NoCheatPlusCompatibility;
import com.github.crashdemons.playerheads.compatibility.plugins.ProtectionPluginCompatibility;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Class providing methods and information for inter-plugin compatibility.
 * 
 * This class may attempt to load some present configuration values.
 * You can avoid this using your plugin's config directly by using the
 * init(plugin,config) method.
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public final class CompatiblePlugins {

    private CompatiblePlugins() {
    }
    /**
     * NoCheatPlus compatibility class instance
     *
     * @see
     * com.github.crashdemons.playerheads.compatibility.plugins.NoCheatPlusCompatibility
     */
    public static NoCheatPlusCompatibility nocheatplus = null;
    /**
     * Generic protection-plugin compatibility class instance
     *
     * @see
     * com.github.crashdemons.playerheads.compatibility.plugins.ProtectionPluginCompatibility
     */
    public static ProtectionPluginCompatibility protection = null;
    /** Generic head support class that determines handling of detected plugin-heads (eg: being able to ignore them) */
    public static HeadPluginCompatibility heads = null;
    
    private static boolean ready = false;
    private static Plugin parentPlugin = null;

    /**
     * Initialize plugin support classes.
     * This should be done during plugin Enable or afterwards - you may need to
     * add a SoftDepend entry for the plugin to be detected in onEnable.
     * <p>
     * Note: it's recommended to call this *after* Compatibility.init so that
     * supported plugin classes have access to other compatibility methods.
     * <p>
     * If this method is used, the top level parent plugin config section is
     * used for plugin-support config entries.
     *
     * @param parentPluginInstance the plugin requesting compatibility support
     */
    public static void init(Plugin parentPluginInstance) {
        CompatiblePlugins.parentPlugin = parentPluginInstance;
        nocheatplus = new NoCheatPlusCompatibility(parentPluginInstance);
        protection = new ProtectionPluginCompatibility(parentPluginInstance);
        heads = new HeadPluginCompatibility(parentPluginInstance);
        reloadConfig();
        ready = true;
    }
    /**
     * Initialize plugin support classes.
     * This should be done during plugin Enable or afterwards - you may need to
     * add a SoftDepend entry for the plugin to be detected in onEnable.
     * <p>
     * Note: it's recommended to call this *after* Compatibility.init so that
     * supported plugin classes have access to other compatibility methods.
     *
     * @param parentPluginInstance the plugin requesting compatibility support
     * @param config the configuration section to use for plugin-support entries
     */
    public static void init(Plugin parentPluginInstance, ConfigurationSection config) {
        CompatiblePlugins.parentPlugin = parentPluginInstance;
        nocheatplus = new NoCheatPlusCompatibility(parentPluginInstance);
        protection = new ProtectionPluginCompatibility(parentPluginInstance);
        heads = new HeadPluginCompatibility(parentPluginInstance,config);
        reloadConfig();
        ready = true;
    }
    
    /**
     * Reloads config for plugin-support modules.
     * Uses the config specified in init().
     */
    public static void reloadConfig(){
        nocheatplus.reloadConfig();
        protection.reloadConfig();
        heads.reloadConfig();
    }

    /**
     * Test of a simulated block break succeeds (considering all applicable
     * plugin support classes).
     * This method includes exempting fastbreak in NCP before testing.
     *
     * @param block the block being broken
     * @param player the player doing the breaking
     * @return whether the block break succeeded or failed (was cancelled).
     */
    public static boolean testBlockBreak(Block block, Player player) {
        boolean isNotExempt = false;
        if (nocheatplus.isPresent()) {
            isNotExempt = !nocheatplus.isExemptFastbreak(player);
            if (isNotExempt) {
                nocheatplus.exemptFastbreak(player);
            }
        }

        boolean blockBreakSucceeded = protection.testBlockBreak(block, player);

        if (nocheatplus.isPresent() && isNotExempt) {
            nocheatplus.unexemptFastbreak(player);
        }
        return blockBreakSucceeded;
    }

    /**
     * Checks whether the plugin compatibility classes are ready for use.
     * (whether the class init completed without exception)
     *
     * @return whether the class is ready
     */
    public static boolean isReady() {
        return ready;
    }

}
