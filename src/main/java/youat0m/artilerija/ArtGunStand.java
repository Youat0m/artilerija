package youat0m.artilerija;

import net.minecraft.network.chat.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

import youat0m.artilerija.nms.NMSEnity;

//todo переписать под интерфейс
public class ArtGunStand implements IArtGun {

    private final static Artilerija plugin = Artilerija.getInstance();

    private final float maxCharge;
    private final float spread;
    private Cartridge cartridge = Cartridge.getEmpty();
    private Entity stand;
    private final Component name;
    private final EntityType type;

    public ArtGunStand(float maxCharge, float spread, Component name) {
        this.maxCharge = maxCharge;
        this.spread = spread;
        this.name = name;
        this.type = EntityType.ZOMBIE;
    }

    public ArtGunStand(Entity entity) throws NullPointerException{
        if(!check(entity)) throw new NullPointerException();
        PersistentDataContainer container = entity.getPersistentDataContainer();
        this.spread = container.get(ArtGunStand.plugin.getSpreadKey(), PersistentDataType.FLOAT);
        this.maxCharge = container.get(ArtGunStand.plugin.getMaxChargeKey(), PersistentDataType.FLOAT);
        this.cartridge = container.get(ArtGunStand.plugin.getProjectileKey(), Cartridge.getEmpty());
        this.name = ((CraftEntity)stand).getHandle().getName();
        this.type = entity.getType();
        this.stand = entity;
    }

    public static boolean check(Entity entity){
        PersistentDataContainer container = entity.getPersistentDataContainer();
        return container.has(plugin.getSpreadKey()) && container.has(plugin.getProjectileKey()) &&
                container.has(plugin.getMaxChargeKey());
    }

    @Override
    public Entity create(Location loc){
        if(stand ==  null) {
            var a = new NMSEnity(loc, this.name, net.minecraft.world.entity.EntityType.ZOMBIE);
            stand = a.getBukkitEntity();
            ((CraftWorld)loc.getWorld()).getHandle().addFreshEntity(a, CreatureSpawnEvent.SpawnReason.CUSTOM);
        }else{
            stand.teleport(loc);
        }
        PersistentDataContainer container = stand.getPersistentDataContainer();
        container.set(plugin.getSpreadKey(), PersistentDataType.FLOAT, spread);
        container.set(plugin.getMaxChargeKey(), PersistentDataType.FLOAT, maxCharge);
        container.set(plugin.getProjectileKey(), Cartridge.getEmpty(), cartridge);
        return this.stand;
    }

    @Override
    public boolean isExist(){
        return stand != null;
    }

    @Override
    public Entity getEntity(){
        return stand;
    }

    @Override
    public float getMaxCharge() {
        return this.maxCharge;
    }

    @Override
    public float getSpread() {
        return this.spread;
    }

    @Override
    public Boolean shoot(){
        if(cartridge != null && cartridge.getPower() != 0){
            if(cartridge.getCharge() >= maxCharge) blowUp();
            float speed = cartridge.getCharge() / cartridge.getWeight();
            Location loc = ((LivingEntity) stand).getEyeLocation();
            Arrow arr = loc.getWorld().spawnArrow(loc, loc.getDirection(), speed,
                    this.spread);
            arr.setDamage(cartridge.getPower());
            arr.setColor(Color.RED);
            arr.setGlowing(true);
            arr.customName(net.kyori.adventure.text.Component.text("atrtelerija"));
            cartridge = Cartridge.getEmpty();
            if(isExist())
                stand.getPersistentDataContainer().set(plugin.getProjectileKey(), Cartridge.getEmpty(), cartridge);
            return true;
        }
        return false;
    }
    @Override
    public void point(Location loc){
        point(loc.getPitch(), loc.getYaw());
    }

    @Override
    public void point(float pitch, float yaw){
        if(stand != null){
            Location loc = stand.getLocation();
            loc.setPitch(pitch);
            loc.setYaw(yaw);
            stand.teleport(loc);
        }
    }

    @Override
    public void setCartridge(Cartridge cartridge1) {

    }

    @Override
    public Cartridge getCartridge() {
        return null;
    }

    public void reload(Cartridge cartridge1){
        if(cartridge1.getPower()==0 && cartridge1.getWeight() == 0 )
            this.cartridge.setCharge(cartridge1.getCharge());
        else if(!cartridge.equals(Cartridge.getEmpty())) {
            stand.getWorld().dropItem(stand.getLocation(), Cartridge.create(cartridge));
            this.cartridge = cartridge1;
        }else
            this.cartridge = cartridge1;
        if (isExist())
            stand.getPersistentDataContainer().set(plugin.getProjectileKey(), Cartridge.getEmpty(), cartridge);
    }


}
