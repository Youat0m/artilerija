package youat0m.artilerija;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minecraft.world.entity.EntityType;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import youat0m.artilerija.nms.NMSEnity;

import java.io.Serializable;

//todo переписать под интерфейс
public class ArtGunStand implements Serializable {

    private final static Artilerija plugin = Artilerija.getInstance();

    private final float maxCharge;
    private final float spread;
    private Cartridge cartridge = Cartridge.getEmpty();
    private transient Entity stand;
    private final String name;
    private final transient EntityType<?> type;

    public ArtGunStand(float maxCharge, float spread, String name) {
        this.maxCharge = maxCharge;
        this.spread = spread;
        this.name = name;
        this.type = EntityType.ZOMBIE;
    }

    public ArtGunStand(Entity entity) throws NullPointerException{
        if(!check(entity)) throw new NullPointerException();
        PersistentDataContainer container = entity.getPersistentDataContainer();
        this.stand = entity;
        this.spread = container.get(ArtGunStand.plugin.getSpreadKey(), PersistentDataType.FLOAT);
        this.maxCharge = container.get(ArtGunStand.plugin.getMaxChargeKey(), PersistentDataType.FLOAT);
        this.cartridge = container.get(ArtGunStand.plugin.getProjectileKey(), Cartridge.getEmpty());
        this.name = PlainTextComponentSerializer.plainText().serialize(stand.customName());
        this.type = ((CraftEntity)entity).getHandle().getType();

    }

    public static boolean check(Entity entity){
        PersistentDataContainer container = entity.getPersistentDataContainer();
        return container.has(plugin.getSpreadKey()) && container.has(plugin.getProjectileKey()) &&
                container.has(plugin.getMaxChargeKey());
    }

    public Entity create(Location loc, String type){
        var a = new NMSEnity(loc, EntityType.byString("minecraft:" + type).orElseThrow());
        stand = a.getBukkitEntity();
        ((CraftWorld)loc.getWorld()).getHandle().addFreshEntity(a, CreatureSpawnEvent.SpawnReason.CUSTOM);
        PersistentDataContainer container = stand.getPersistentDataContainer();
        container.set(plugin.getSpreadKey(), PersistentDataType.FLOAT, spread);
        container.set(plugin.getMaxChargeKey(), PersistentDataType.FLOAT, maxCharge);
        container.set(plugin.getProjectileKey(), Cartridge.getEmpty(), cartridge);
        stand.setCustomNameVisible(true);
        stand.customName(Component.text(name));
        return this.stand;
    }

     
    public Entity create(Location loc) {
        return create(loc, type.id);
    }

     
    public boolean isExist(){
        return stand != null;
    }

     
    public Entity getEntity(){
        return stand;
    }

     
    public float getMaxCharge() {
        return this.maxCharge;
    }

     
    public float getSpread() {
        return this.spread;
    }


    public void point(Location loc){
        point(loc.getPitch(), loc.getYaw());
    }

     
    public void point(float pitch, float yaw){
        if(stand != null){
            Location loc = stand.getLocation();
            loc.setPitch(pitch);
            loc.setYaw(yaw);
            stand.teleport(loc);
        }
    }

     
    public void setCartridge(Cartridge cartridge1) {
        cartridge = cartridge1;
    }

     
    public EntityType<?> getType() {
        return type;
    }

    public void blowUp(){
        getEntity().getLocation().createExplosion(getCartridge().getCharge());
    }
     
    public Cartridge getCartridge() {
        return cartridge;
    }

    public void reload(Cartridge cartridge1){
        if(cartridge1.getPower()==0 && cartridge1.getWeight() == 0 )
            this.cartridge.setCharge(cartridge1.getCharge());
        else if(!cartridge.equals(Cartridge.getEmpty())) {
            stand.getWorld().dropItem(stand.getLocation(), Cartridge.createItem(cartridge));
            this.cartridge = cartridge1;
        }else
            this.cartridge = cartridge1;
        if (isExist())
            stand.getPersistentDataContainer().set(plugin.getProjectileKey(), Cartridge.getEmpty(), cartridge);
    }

     public Boolean shoot(){
        if(getCartridge() != null && getCartridge().getPower() > 0){
            if(getCartridge().getCharge() >= getMaxCharge()) blowUp();
            float speed = getCartridge().getCharge() / getCartridge().getWeight();
            Location loc = ((LivingEntity) getEntity()).getEyeLocation();
            Arrow arr = loc.getWorld().spawnArrow(loc, loc.getDirection(), speed,
                    getSpread());
            arr.setDamage(getCartridge().getPower());
            arr.setColor(Color.RED);
            arr.setGlowing(true);
            arr.customName(Component.text("artilerija"));
            arr.getPersistentDataContainer().set(Artilerija.getInstance().getProjectileKey(), Cartridge.getEmpty(), getCartridge());
            setCartridge(Cartridge.getEmpty());
            if(isExist())
                getEntity().getPersistentDataContainer().set(Artilerija.getInstance().getProjectileKey(), Cartridge.getEmpty(), getCartridge());
            return true;
        }
        return false;
    }




}
