/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.spigot_1_21;

import com.github.crashdemons.playerheads.compatibility.*;
import com.github.crashdemons.playerheads.compatibility.SkullType;
import com.github.crashdemons.playerheads.compatibility.common.SkullDetails_modern;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * CompatibilityProvider Implementation for 1.13+ support
 * @author crashdemons (crashenator at gmail.com)
 */
@SuppressWarnings( "deprecation" )
public class Provider implements CompatibilityProvider {

    public Provider(){}
    @Override public String getType(){ return "com/github/crashdemons/playerheads/compatibility/craftbukkit"; }
    @Override public String getVersion(){ return "1.14+"; }

    //======common and modern support =========
    @Override
    public String getOwnerDirect(SkullMeta skullItemMeta) {
        return skullItemMeta.getOwner();
    }

    @Override
    public String getOwnerDirect(Skull skullBlockState) {
        return skullBlockState.getOwner();
    }

    @Override
    public boolean setOwner(SkullMeta skullItemMeta, String owner) {
        return skullItemMeta.setOwner(owner);
    }

    @Override
    public boolean setOwner(Skull skullBlockState, String owner) {
        return skullBlockState.setOwner(owner);
    }

    @Override
    public boolean isHead(ItemStack s) {
        return getSkullType(s) != null;
    }

    @Override
    public boolean isHead(BlockState s) {
        return getSkullType(s) != null;
    }

    @Override
    public boolean isPlayerhead(ItemStack s) {
        return getSkullType(s) == SkullType.PLAYER;
    }

    @Override
    public boolean isPlayerhead(BlockState s) {
        return getSkullType(s) == SkullType.PLAYER;
    }

    @Override
    public boolean isMobhead(ItemStack s) {
        SkullType t = getSkullType(s);
        return (t != null && t != SkullType.PLAYER);
    }

    @Override
    public boolean isMobhead(BlockState s) {
        SkullType t = getSkullType(s);
        return (t != null && t != SkullType.PLAYER);
    }

    @Override
    public String getCompatibleNameFromEntity(Entity e) {
        if(isZombiePigman(e)) return "ZOMBIFIED_PIGLIN";
        if (isLegacyCat(e)) {
            return "CAT";
        }
        return e.getType().name().toUpperCase();
    }

    @Override
    public OfflinePlayer getOfflinePlayerByName(String username) {
        return Bukkit.getOfflinePlayer(username);
    }

    protected static final String ETYPE_ZOMBIE_PIGMAN_POST116 = "ZOMBIFIED_PIGLIN";
    @Override
    public EntityType getEntityTypeFromTypename(String typename){
        try{
            return EntityType.valueOf(typename.toUpperCase());
        }catch(Exception e){
            return null;
        }
    }





    protected EntityType getCurrentZombiePigmanType(){
        return EntityType.ZOMBIFIED_PIGLIN;
    }




    protected boolean isZombiePigman(Entity e){
        return (e instanceof PigZombie);
    }

    protected boolean isLegacyCat(Entity e) {
        return false;
    }



    //-----------5.2.12 providers-----------//
    @Override
    public Optional<Object> getOptionalProfile(SkullMeta skullMeta){
        PlayerProfile pp = skullMeta.getOwnerProfile();
        if(pp==null) return Optional.empty();
        return Optional.of(pp);
    }
    @Override
    public Optional<Object> getOptionalProfile(Skull skullState){
        PlayerProfile pp = skullState.getOwnerProfile();
        if(pp==null) return Optional.empty();
        return Optional.of(pp);
    }
    @Override
    public boolean setOptionalProfile(Skull skullState, Optional<Object> profile){
        if(profile.isEmpty()) return false;
        skullState.setOwnerProfile((PlayerProfile)profile.get());
        return true;
    }
    @Override
    public boolean setOptionalProfile(SkullMeta skullMeta, Optional<Object> profile){
        if(profile.isEmpty()) return false;
        skullMeta.setOwnerProfile((PlayerProfile)profile.get());
        return true;
    }

    @Override
    public ItemStack getCompatibleHeadItem(@NotNull CompatibleSkullMaterial material, int amount){
        return material.getDetails().createItemStack(amount);
    }


    @Override
    public boolean isCustomHead(String username, UUID id){
        if(id!=null) if( (username==null || username.isEmpty()) ) return true;//custom head would have a valid ID, but no username (no user+no ID = boring steve head / Player head)
        if(username!=null){
            if(username.contains(":")) return true;//Invalid char for names, but used by a few large plugins (DropHeads)
            if(username.equalsIgnoreCase("HeadDB") || username.equalsIgnoreCase("hdb")) return true;
        }
        return false;
    }

    @Override
    public boolean isCustomHead(PlayerProfile profile){
        if(profile==null) throw new IllegalArgumentException("profile is null");
        return isCustomHead(profile.getName(), profile.getUniqueId());
    }
    @Override
    public boolean isCustomHead(Object skull){
        if(skull==null) throw new IllegalArgumentException("skull is null");
        if(skull instanceof Skull || skull instanceof SkullMeta){
            PlayerProfile profile = Compatibility.getProvider().getPlayerProfile(skull);
            if(profile==null) return false;
            return isCustomHead(profile);
        }else throw new IllegalArgumentException("skull provided is not of type Skull or SkullMeta");
    }


    protected Entity getEntityOwningEntity(EntityDamageByEntityEvent event, boolean considertameowners){
        Entity entity = event.getDamager();
        if(entity instanceof Projectile){
            //System.out.println("   damager entity projectile");
            Projectile projectile = (Projectile) entity;
            ProjectileSource shooter = projectile.getShooter();
            if(shooter instanceof Entity){
                entity=(Entity) shooter;
                //if(entity!=null) System.out.println("   arrow shooter: "+entity.getType().name()+" "+entity.getName());
            }
        }else if(entity instanceof Tameable && considertameowners){
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

    public LivingEntity getKillerEntity(EntityDeathEvent event, boolean considermobkillers, boolean considertameowners){
        LivingEntity victim = event.getEntity();
        //if(victim!=null) System.out.println("victim: "+victim.getType().name()+" "+victim.getName());
        LivingEntity killer = victim.getKiller();
        //if(killer!=null) System.out.println("original killer: "+killer.getType().name()+" "+killer.getName());

        if(killer==null && considermobkillers){
            EntityDamageEvent dmgEvent = event.getEntity().getLastDamageCause();
            if(dmgEvent instanceof EntityDamageByEntityEvent){
                Entity killerEntity = getEntityOwningEntity((EntityDamageByEntityEvent)dmgEvent, considertameowners);
                //if(killerEntity!=null) System.out.println(" parent killer: "+killerEntity.getType().name()+" "+killerEntity.getName());
                if(killerEntity instanceof LivingEntity) killer=(LivingEntity)killerEntity;
                //what if the entity isn't living (eg: arrow?)
            }
        }
        //if(killer!=null) System.out.println(" final killer: "+killer.getType().name()+" "+killer.getName());
        return killer;
    }

    protected boolean setTemporaryTag(Entity ent, Plugin plugin, String key, String value){
        if(ent instanceof Metadatable){
            ent.setMetadata(key, new FixedMetadataValue(plugin,value));
            return true;
        }
        return false;
    }
    protected String getTemporaryTag(Entity ent, Plugin plugin, String key){
        if(ent instanceof Metadatable){
            List<MetadataValue> values = ent.getMetadata(key);
            for(MetadataValue value : values){
                Plugin valuePlugin = value.getOwningPlugin();
                if(valuePlugin==plugin || (valuePlugin!=null && valuePlugin.equals(plugin))){
                    return value.asString();
                }
            }
        }
        return null;
    }



    @Override
    public boolean setEntityTag(Entity entity, Plugin plugin, String key, String value, boolean persistent){
        if(persistent) return setPersistentTag(entity,plugin,key,value);
        return setTemporaryTag(entity,plugin,key,value);
    }

    @Override
    public String getEntityTag(Entity entity, Plugin plugin, String key, boolean persistent){
        if(persistent) return getPersistentTag(entity,plugin,key);
        return getTemporaryTag(entity,plugin,key);
    }


    @Override public ItemStack getItemInMainHand(LivingEntity p){
        EntityEquipment equipment = p.getEquipment();
        if(equipment == null) return null;
        return equipment.getItemInMainHand();
    }
    @Override public ItemStack getItemInMainHand(Player p){ return p.getEquipment().getItemInMainHand(); }
    @Override public void setItemInMainHand(Player p,ItemStack s){ p.getEquipment().setItemInMainHand(s); }
    @Override public SkullDetails getSkullDetails(SkullType type){ return new SkullDetails_modern(type); }
    @Override public boolean getKeepInventory(World world){ return world.getGameRuleValue(GameRule.KEEP_INVENTORY); }
    @Override public SkullType getSkullType(ItemStack s){ return getSkullType(s.getType()); }
    @Override public SkullType getSkullType(BlockState s){ return getSkullType(s.getType()); }

    public SkullType getSkullType(Material mat){
        String typeName = mat.name();
        typeName=typeName.replaceFirst("_WALL", "").replaceFirst("_HEAD", "").replaceFirst("_SKULL", "");
        return RuntimeReferences.getSkullTypeByName(typeName);
    }
    //==============1.13 provider===============================================

    @Override public OfflinePlayer getOwningPlayerDirect(SkullMeta skullItemMeta){ return skullItemMeta.getOwningPlayer(); }
    @Override public OfflinePlayer getOwningPlayerDirect(Skull skullBlockState){ return skullBlockState.getOwningPlayer(); }
    //@Override public String getOwnerDirect(SkullMeta skullItemMeta){ return skullItemMeta.getOwner(); }
    //@Override public String getOwnerDirect(Skull skullBlockState){ return skullBlockState.getOwner(); }
    @Override public boolean setOwningPlayer(SkullMeta skullItemMeta, OfflinePlayer op){ return skullItemMeta.setOwningPlayer(op); }
    @Override public void    setOwningPlayer(Skull skullBlockState, OfflinePlayer op){ skullBlockState.setOwningPlayer(op); }
    //@Override public boolean setOwner(SkullMeta skullItemMeta, String owner){ return skullItemMeta.setOwner(owner); }
    //@Override public boolean setOwner(Skull skullBlockState, String owner){ return skullBlockState.setOwner(owner); }


    @Override public OfflinePlayer getOwningPlayer(SkullMeta skull){
        OfflinePlayer op = getOwningPlayerDirect(skull);//skullMeta.getOwningPlayer();
        if(op!=null) return op;
        return getOwningPlayer(skull.getOwnerProfile()) ;
    }
    @Override public OfflinePlayer getOwningPlayer(Skull skull){
        OfflinePlayer op = getOwningPlayerDirect(skull);//skullMeta.getOwningPlayer();
        if(op!=null) return op;
        return getOwningPlayer(skull.getOwnerProfile()) ;
    }

    private OfflinePlayer getOwningPlayer(@Nullable PlayerProfile pp){
        if(pp==null) return null;
        UUID id = pp.getUniqueId();
        if(id!=null) return Bukkit.getOfflinePlayer(id);
        String name = pp.getName();
        if(name!=null) return Bukkit.getOfflinePlayer(name);
        return null;
    }


    @Override public String getOwner(SkullMeta skull){
        String owner=null;
        OfflinePlayer op = getOwningPlayerDirect(skull);//skullMeta.getOwningPlayer();
        if(op==null) op = this.getOwningPlayer(skull);//this does happen on textured heads with a profile but without an OwningPlayer
        if(op!=null) owner=op.getName();
        if(owner==null) owner=getOwnerDirect(skull);//skullMeta.getOwner();
        return owner;
    }
    @Override public String getOwner(Skull skull){
        String owner=null;
        OfflinePlayer op = getOwningPlayerDirect(skull);//skullMeta.getOwningPlayer();
        if(op==null) op = this.getOwningPlayer(skull);//this does happen on textured heads with a profile but without an OwningPlayer
        if(op!=null) owner=op.getName();
        if(owner==null) owner=getOwnerDirect(skull);//skullMeta.getOwner();
        return owner;
    }

    @Override public boolean setProfile(SkullMeta headMeta, @NotNull UUID uuid, @NotNull String username, URL texture){
        try{
            headMeta.setOwnerProfile(this.createPlayerProfile(username,uuid,texture));
        }catch (Exception e){
            return false;
        }
        return false;
    }
    @Override public boolean setProfile(Skull headBlockState, @NotNull UUID uuid, @NotNull String username, URL texture){
        try{
            headBlockState.setOwnerProfile(this.createPlayerProfile(username,uuid,texture));
        }catch (Exception e){
            return false;
        }
        return false;
    }

    //-----------5.2.12 providers-----------//


    //--------5.2.13 providers -----//

    @Override
    public boolean setPlayerProfile(Object skull, PlayerProfile profile) throws IllegalArgumentException{
        if(skull instanceof Skull sb) try{ sb.setOwnerProfile(profile); return true; }catch (Exception e){ return false; }
        if(skull instanceof SkullMeta sm) try{ sm.setOwnerProfile(profile); return true; }catch (Exception e){ return false; }
        return false;
    }
    @Override
    public PlayerProfile getPlayerProfile(Object skull) throws IllegalArgumentException{
        if(skull instanceof Skull sb) return sb.getOwnerProfile();
        if(skull instanceof SkullMeta sm) return sm.getOwnerProfile();
        return null;
    }

    @Override
    public PlayerProfile createPlayerProfile(@Nullable String name, @Nullable UUID id, @Nullable URL texture){
        PlayerProfile pp = Bukkit.getServer().createPlayerProfile(id,name);
        PlayerTextures pt = pp.getTextures();
        pt.setSkin(texture);
        pp.setTextures(pt);
        return pp;
    }

    @Override
    public boolean clearProfile(Object skull) throws IllegalArgumentException{
        PlayerProfile pp = this.getPlayerProfile(skull);
        if(pp==null) return true;
        if(skull instanceof Skull sb){ sb.setOwnerProfile(null); return true; }
        if(skull instanceof SkullMeta sm){ sm.setOwnerProfile(null); return true; }
        return false;
    }
    //========1.14 Provider ============


    
    

    protected boolean setPersistentTag(Entity entity, Plugin plugin, String key, String value){
        NamespacedKey nskey = new NamespacedKey(plugin,key);
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        pdc.set(nskey, PersistentDataType.STRING, value);
        return true;
    }
    protected String getPersistentTag(Entity entity, Plugin plugin, String key){
        NamespacedKey nskey = new NamespacedKey(plugin,key);
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        return pdc.get(nskey, PersistentDataType.STRING);
    }
    
    @Override
    public boolean supportsEntityTagType(boolean persistent){
        return true;
    }
}
