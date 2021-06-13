# PlayerHeads
Bukkit Plugin - Drops a player's head when s/he dies, also mob heads
* Drop player heads on player death
* Drop mob heads on death
* Configure drop rate chances
* Configure whether player-kills are required to drop

All credit goes to meiskam for creating the plugin and zand, Dragoboss, any many others for maintaining it.

# 5.x Branch
Same simple plugin, new improvements.


# Where's the Source!?
The main project combines different code from multiple modules - you need to look into those module directories for the code.

For the code behind core plugin behavior see the **PlayerHeads-core** module.

For an API to use the plugin from your own plugins there is also a lighter-weight API available from the **PlayerHeads-api** module.

Otherwise, you can check out the **-support** modules for any server-specific implementation code


# Importing the project into Eclipse (as of version 2021-03/4.19)
 * In the menu click `File`  then `Import ...`
 * On the new `Select` window, expand the `Maven` option and select `Existing Maven Projects` and click `Next`
 * Browse to and select the folder you cloned the project into (do not enter into it)
 * You should see a list of POMs to import as projects.
 * Optional - for a cleaner workspace
   * Click "Deselect All"
   * Check only the first /pom.xml entry
 * Alternatively - for a separate project for each module:
   * All POMs should be already checked by default.
 * Click Finish
 * Now you can built the project with the `Run` menu by selecting Maven Install or Maven Build.
  
