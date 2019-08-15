/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
package org.shininet.bukkit.playerheads;

import com.github.crashdemons.playerheads.BroadcastManager;
import com.github.crashdemons.playerheads.DropManager;
import com.github.crashdemons.playerheads.BlockDropResult;
import com.github.crashdemons.playerheads.antispam.InteractSpamPreventer;
import com.github.crashdemons.playerheads.SkullConverter;
import com.github.crashdemons.playerheads.SkullManager;
import com.github.crashdemons.playerheads.TexturedSkullType;
import com.github.crashdemons.playerheads.antispam.PlayerDeathSpamPreventer;
import com.github.crashdemons.playerheads.compatibility.Compatibility;
import com.github.crashdemons.playerheads.compatibility.CompatiblePlugins;
import com.github.crashdemons.playerheads.compatibility.plugins.SimulatedBlockBreakEvent;
import java.util.LinkedHashMap;

import java.util.Map;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.shininet.bukkit.playerheads.events.MobDropHeadEvent;
import org.shininet.bukkit.playerheads.events.PlayerDropHeadEvent;

import java.util.function.Predicate;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Tameable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.projectiles.ProjectileSource;
import org.shininet.bukkit.playerheads.events.HeadRollEvent;
import org.shininet.bukkit.playerheads.events.LivingEntityDropHeadEvent;
import org.shininet.bukkit.playerheads.events.modifiers.DropRateModifier;
import org.shininet.bukkit.playerheads.events.modifiers.DropRateModifierType;

/**
 * Defines a listener for playerheads events.
 * <p>
 * <i>Note:</i> This documentation was inferred after the fact and may be
 * inaccurate.
 *
 * @author meiskam
 */
class PlayerHeadsListener implements Listener {

    private final Random prng = new Random();
    private final PlayerHeads plugin;
    private volatile InteractSpamPreventer clickSpamPreventer;
    private volatile PlayerDeathSpamPreventer deathSpamPreventer;
    
    

    public void registerAll() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void unregisterAll() {
        EntityDeathEvent.getHandlerList().unregister(this);
        PlayerInteractEvent.getHandlerList().unregister(this);
        PlayerJoinEvent.getHandlerList().unregister(this);
        BlockBreakEvent.getHandlerList().unregister(this);
    }

    private final Predicate<ItemStack> isVanillaHead = new Predicate<ItemStack>() { //we only need this because of java 7 support
        @Override
        public boolean test(ItemStack itemStack) {
            return Compatibility.getProvider().isHead(itemStack);
        }
    };

    protected PlayerHeadsListener(PlayerHeads plugin) {
        this.plugin = plugin;
        reloadConfig();
    }

    public void reloadConfig() {
        org.bukkit.configuration.file.FileConfiguration cfg = plugin.configFile; //just to make the following more readable
        clickSpamPreventer = new InteractSpamPreventer(cfg.getInt("clickspamcount"), cfg.getLong("clickspamthreshold"));
        deathSpamPreventer = new PlayerDeathSpamPreventer(cfg.getInt("deathspamcount"), cfg.getLong("deathspamthreshold"));
    }
    
    private Entity getEntityOwningEntity(EntityDamageByEntityEvent event){
        Entity entity = event.getDamager();
        if(entity instanceof Projectile){
            //System.out.println("   damager entity projectile");
            Projectile projectile = (Projectile) entity;
            ProjectileSource shooter = projectile.getShooter();
            if(shooter instanceof Entity){
                entity=(Entity) shooter;
                //if(entity!=null) System.out.println("   arrow shooter: "+entity.getType().name()+" "+entity.getName());
            }
        }else if(entity instanceof Tameable && plugin.configFile.getBoolean("considertameowner")){
            //System.out.println("   damager entity wolf");
            Tameable animal = (Tameable) entity;
            if(animal.isTamed()){
                AnimalTamer tamer = animal.getOwner();
                if(tamer instanceof Entity){
                    entity=(Entity) tamer;
                    //if(entity!=null) System.out.println("   wolf tamer: "+entity.getType().name()+" "+entity.getName());
                }
            }
        }
        return entity;
    }
    
    private LivingEntity getKillerEntity(EntityDeathEvent event){
        LivingEntity victim = event.getEntity();
        //if(victim!=null) System.out.println("victim: "+victim.getType().name()+" "+victim.getName());
        LivingEntity killer = victim.getKiller();
        //if(killer!=null) System.out.println("original killer: "+killer.getType().name()+" "+killer.getName());
        
        if(killer==null && plugin.configFile.getBoolean("considermobkillers")){
            EntityDamageEvent dmgEvent = event.getEntity().getLastDamageCause();
            if(dmgEvent instanceof EntityDamageByEntityEvent){
                Entity killerEntity = getEntityOwningEntity((EntityDamageByEntityEvent)dmgEvent);
                //if(killerEntity!=null) System.out.println(" parent killer: "+killerEntity.getType().name()+" "+killerEntity.getName());
                if(killerEntity instanceof LivingEntity) killer=(LivingEntity)killerEntity;
                //what if the entity isn't living (eg: arrow?)
            }
        }
        //if(killer!=null) System.out.println(" final killer: "+killer.getType().name()+" "+killer.getName());
        return killer;
    }
    

    /**
     * Event handler for entity deaths.
     * <p>
     * Used to determine when heads should be dropped.
     *
     * @param event the event received
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity victim = event.getEntity();
        LivingEntity killer = getKillerEntity(event);
        
        LinkedHashMap<String,DropRateModifier> modifiers = new LinkedHashMap<>();

        if (killer != null) {
            if(killer instanceof Creeper && !(victim instanceof Player)){
                if(((Creeper) killer).isPowered()){
                    String chargedcreeperBehavior = plugin.configFile.getString("chargedcreeperbehavior");
                    Double chargedcreeperModifier = plugin.configFile.getDouble("chargedcreepermodifier");
                    
                    if(chargedcreeperBehavior.equals("block") || chargedcreeperBehavior.equals("replace"))
                        event.getDrops().removeIf(isVanillaHead);
                    if(chargedcreeperBehavior.equals("block") || chargedcreeperBehavior.equals("vanilla"))
                        return;
                    modifiers.put("chargedcreeper", new DropRateModifier(DropRateModifierType.MULTIPLY,chargedcreeperModifier));
                }
            }
            
            ItemStack weapon = Compatibility.getProvider().getItemInMainHand(killer);//killer.getEquipment().getItemInMainHand();
            if(weapon!=null){
                if(plugin.configFile.getBoolean("requireitem")){
                    String weaponType = weapon.getType().name().toLowerCase();
                    if(!plugin.configFile.getStringList("requireditems").contains(weaponType)) return;
                }
                double lootingRate = plugin.configFile.getDouble("lootingrate");
                int lootingLevel = weapon.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
                modifiers.put("looting", new DropRateModifier(DropRateModifierType.ADD_MULTIPLE_PER_LEVEL,lootingRate,lootingLevel));
            }
        }

        TexturedSkullType skullType = SkullConverter.skullTypeFromEntity(victim);
        if (skullType == null) {
            return;//entity type is one we don't support - don't attempt to handle heads for it.
        }
        String mobDropConfig = skullType.getConfigName();
        Double droprate = plugin.configFile.getDouble(mobDropConfig);
        if (droprate < 0) {
            return;//if droprate is <0, don't modify drops
        }
        switch (skullType) {
            case PLAYER:
                if (plugin.configFile.getBoolean("nerfdeathspam")) {
                    if (deathSpamPreventer.recordEvent(event).isSpam()) {
                        return;
                    }
                }
                break;
            case WITHER_SKELETON:
                String witherskeletonbehavior = plugin.getConfig().getString("witherskeletonbehavior");
                if(witherskeletonbehavior.equals("block") || witherskeletonbehavior.equals("replace")) 
                    event.getDrops().removeIf(isVanillaHead);
                if(witherskeletonbehavior.equals("block") || witherskeletonbehavior.equals("vanilla"))
                    return;
                
                break;
            case SLIME:
            case MAGMA_CUBE:
                Double slimemodifier=1.0;
                Entity entity = event.getEntity();
                if(entity instanceof Slime){
                    int slimeSize = ((Slime) entity).getSize();// 1, 2, 3, 4  (1,2,4 natual with 1 the smallest)
                    double slimeModifier=plugin.configFile.getDouble("slimemodifier."+slimeSize);
                    modifiers.put("chargedcreeper", new DropRateModifier(DropRateModifierType.MULTIPLY,slimeModifier));
                }
                break;
        }
        doHeadRoll(event, skullType, droprate, modifiers);
    }
    
    private void doHeadRoll(EntityDeathEvent event, TexturedSkullType type, Double droprateOriginal, Map<String,DropRateModifier> modifiers){
        Double dropchanceRand = prng.nextDouble();
        Player killer = event.getEntity().getKiller();
        LivingEntity entity = event.getEntity();
        boolean isPlayerDeath = (entity instanceof Player);
        
        String permBehead = "canbehead";
        String permAlwaysBehead = "alwaysbehead";
        String configPkOnly = "pkonly";
        if(isPlayerDeath){
            if(!((Player) entity).hasPermission("playerheads.canlosehead")) return;//target can't lose head.
        }else{
            permBehead+="mob";
            permAlwaysBehead+="mob";
            configPkOnly="mob"+configPkOnly;
        }

        boolean killerAlwaysBeheads = false;
        if (killer != null) {//target was killed by a player
            if (!killer.hasPermission(permBehead)) return;//killer does not have permission to behead this target in any case
            killerAlwaysBeheads = killer.hasPermission(permAlwaysBehead);
        }
        if(killer == null || killer.getUniqueId()==entity.getUniqueId()){//target was killed by non-player, or player killed themself.
            if (plugin.configFile.getBoolean(configPkOnly)) return;//mobs must only be beheaded by players
        }
        
        HeadRollEvent rollEvent = new HeadRollEvent(killer, entity, killerAlwaysBeheads,dropchanceRand, droprateOriginal);
        rollEvent.setModifiers(modifiers);
        rollEvent.recalculateSuccess();

        plugin.getServer().getPluginManager().callEvent(rollEvent);
        if (!rollEvent.succeeded()) {
            return;//allow plugins a chance to modify the success
        }

        ItemStack drop = plugin.api.getHeadDrop(entity);

        LivingEntityDropHeadEvent dropHeadEvent;
        if(isPlayerDeath){
            dropHeadEvent = new PlayerDropHeadEvent((Player) entity, drop);
        }else{
            dropHeadEvent = new MobDropHeadEvent(entity, drop);
        }
        plugin.getServer().getPluginManager().callEvent(dropHeadEvent);
        if (dropHeadEvent.isCancelled()) {
            return;
        }
        
        boolean isWither = (!isPlayerDeath) && Compatibility.getProvider().getCompatibleNameFromEntity(entity).equals("WITHER");
        DropManager.requestDrops(plugin, dropHeadEvent.getDrops(), isWither, event);
        
        //broadcast message about the beheading.
        BroadcastManager.broadcastBehead(plugin, killer, entity);
    }

    /**
     * Event handler for player-block interactions.
     * <p>
     * Used to determine when information should be sent to the user, or
     * head-blocks need updating.
     *
     * @param event the event received
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        if (block != null) {
            BlockState state = block.getState();
            TexturedSkullType skullType = SkullConverter.skullTypeFromBlockState(state);
            if (skullType == null) {
                return;
            }
            //System.out.println(skullType.name());

            if (clickSpamPreventer.recordEvent(event).isSpam()) {
                return;
            }

            if (player.hasPermission("playerheads.clickinfo")) {
                switch (skullType) {
                    case PLAYER:
                        Skull skullState = (Skull) block.getState();
                        if (skullState.hasOwner()) {
                            String owner = Compatibility.getProvider().getOwner(skullState);//SkullConverter.getSkullOwner(skullState);
                            if (owner == null) {
                                return;//this is an unsupported custom-texture head. don't print anything.
                            }
                            //String ownerStrip = ChatColor.stripColor(owner); //Unnecessary?
                            Formatter.formatMsg(player, Lang.CLICKINFO, owner);
                        } else {
                            //player.sendMessage("ClickInfo2 HEAD");
                            Formatter.formatMsg(player, Lang.CLICKINFO2, Lang.HEAD);
                        }
                        break;
                    default:
                        Formatter.formatMsg(player, Lang.CLICKINFO2, skullType.getDisplayName());
                        break;
                }
            }
            SkullManager.updatePlayerSkullState(state);

        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemSpawnEvent(ItemSpawnEvent event) {
        ItemStack stack = event.getEntity().getItemStack();
        //Location location = event.getEntity().getLocation();
        boolean fixDroppedHeads = plugin.configFile.getBoolean("fixdroppedheads");
        if (!fixDroppedHeads) {
            return;
        }
        TexturedSkullType skullType = SkullConverter.skullTypeFromItemStack(stack);
        if (skullType == null) {
            return;
        }
        ItemStack newstack = null;
        boolean addLore = plugin.configFile.getBoolean("addlore");
        switch (skullType) {
            case PLAYER:
                SkullMeta skull = (SkullMeta) stack.getItemMeta();
                String owner = Compatibility.getProvider().getOwner(skull);//SkullConverter.getSkullOwner(skull);
                if (owner == null) {
                    return;//you broke an unsupported custom-textured head. Question: should we instead just return to avoid modifying behavior?
                }
                newstack = SkullManager.PlayerSkull(owner, stack.getAmount(), addLore);
                break;
            default:
                boolean blockIsSkinnable = Compatibility.getProvider().isPlayerhead(stack);
                newstack = DropManager.createConvertedMobhead(plugin,skullType, blockIsSkinnable, addLore, stack.getAmount());
                break;
        }
        if (newstack == null) {
            return;
        }
        event.getEntity().setItemStack(newstack);
    }





    /**
     * Event handler for player block-break events.
     * <p>
     * Used to determine when the associated head item for a head-block needs to
     * be dropped, if it is broken by a player.
     *
     * @param event the event received
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event instanceof SimulatedBlockBreakEvent) {
            return;
        }
        Block block = event.getBlock();
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            BlockState state = block.getState();
            TexturedSkullType skullType = SkullConverter.skullTypeFromBlockState(state);
            if (skullType != null) {

                boolean canBreak = true;
                if (CompatiblePlugins.isReady()) {
                    canBreak = CompatiblePlugins.testBlockBreak(block, player);
                }

                if (!canBreak) {
                    event.setCancelled(true);
                } else {
                    event.setCancelled(true);
                    BlockDropResult result = DropManager.blockDrop(plugin,event, block, state);
                    if (result == BlockDropResult.FAILED_CUSTOM_HEAD) {
                        event.setCancelled(false);//uncancel the event if we can't drop it accurately - attempted fix for issue crashdemons/PlayerHeads#12
                    }
                }
            }

        }
    }

    /**
     * Event handler for player server join events
     * <p>
     * Used to send updater information to appropriate players on join.
     *
     * @param event the event received
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("playerheads.update") && plugin.getUpdateReady()) {
            Formatter.formatMsg(player, Lang.UPDATE1, plugin.getUpdateName());
            Formatter.formatMsg(player, Lang.UPDATE3, "http://curse.com/bukkit-plugins/minecraft/" + Config.updateSlug);
        }
    }
}
