package youat0m.artilerija;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;
import java.util.Optional;

public class ArtGun {

    private final static Artilerija plugin = Artilerija.getInstance();

    private final float maxCharge;
    private float charge;
    private final float spread;
    private Projectile projectile;
    private ArmorStand stand;

    public ArtGun(ArmorStand stand, float spread, float maxCharge, float charge, Projectile projectile) {
        this.spread = spread;
        this.stand = stand;
        this.maxCharge = maxCharge;
        this.charge = charge;
        this.projectile = projectile;
    }

    public ArtGun(float maxCharge, float spread) {
        this.maxCharge = maxCharge;
        this.spread = spread;
    }

    public ArmorStand create(Location loc){
        if(stand ==  null) {
            stand = loc.getWorld().spawn(loc, ArmorStand.class);
        }else{
            stand.teleport(loc);
        }
        PersistentDataContainer container = stand.getPersistentDataContainer();
        container.set(plugin.getSpreadKey(), PersistentDataType.FLOAT, spread);
        container.set(plugin.getMaxChargeKey(), PersistentDataType.FLOAT, maxCharge);
        container.set(plugin.getChargeKey(), PersistentDataType.FLOAT, charge);
        container.set(plugin.getProjectileKey(), new Projectile(), projectile);
        return this.stand;
    }

    public boolean isExist(){
        return stand !=  null;
    }

    public ArmorStand getStand(){
        return stand;
    }

    //todo решить удалять или нет
    //todo eсли нет, то переписать под Optional
    public static ArtGun getInWorld(Location loc){
        Collection<Entity> collection = loc.getWorld().getNearbyEntities(loc, 1, 1, 1, (t -> t instanceof ArmorStand));
        return getFromStand((ArmorStand) collection.stream().filter(
                e -> e.getPersistentDataContainer().has(plugin.getSpreadKey()) &&
                e.getPersistentDataContainer().has(plugin.getPowerKey())).iterator().next()).orElseThrow();

    }
    public static Optional<ArtGun> getFromStand(ArmorStand stand){
        PersistentDataContainer container = stand.getPersistentDataContainer();
        if(container.has(plugin.getChargeKey()) &&
                container.has(plugin.getMaxChargeKey()) &&
                container.has(plugin.getSpreadKey()) &&
                container.has(plugin.getProjectileKey()))
            return Optional.of(new ArtGun(stand,
                container.get(plugin.getSpreadKey(), PersistentDataType.FLOAT),
                container.get(plugin.getMaxChargeKey(), PersistentDataType.FLOAT),
                container.get(plugin.getChargeKey(), PersistentDataType.FLOAT),
                container.get(plugin.getProjectileKey(), new Projectile(0))));
        return Optional.empty();
    }

    public Boolean shoot(){
        if(projectile != null){
            if(charge >= maxCharge) blowUp();
            float speed = this.charge / projectile.getWeight();
            Location loc = stand.getEyeLocation();
            Arrow arr = loc.getWorld().spawnArrow(loc, loc.getDirection(), speed,
                    this.spread);
            arr.setDamage(projectile.getPower());
            arr.setColor(Color.RED);
            arr.setGlowing(true);
            arr.customName(Component.text("atrtelerija"));
            return true;
        }
        return false;
    }
    public void point(Location loc){
        Location location = stand.getLocation();
        location.setPitch(loc.getPitch());
        location.setYaw(loc.getYaw());
        stand.teleport(loc);
    }
    public void reload(Projectile projectile1){
        this.projectile = projectile1;
        stand.getPersistentDataContainer().set(plugin.getProjectileKey(), new Projectile(), projectile1);
    }
    public void reloadPowder(float charge){
        this.charge = charge;
        stand.getPersistentDataContainer().set(plugin.getChargeKey(), PersistentDataType.FLOAT, charge);
    }
    public void blowUp(){
        stand.getWorld().createExplosion(stand.getLocation(), 10);
    }


}
