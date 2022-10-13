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

public class ArtGun {
    private final float maxCharge;
    private float charge;
    private final float spread;
    private Projectile projectile;
    private ArmorStand stand;

    public ArtGun(float spread, ArmorStand stand, float maxCharge) {
        this.spread = spread;
        this.stand = stand;
        this.maxCharge = maxCharge;
    }

    public ArtGun create(Location loc){
        if(stand ==  null) {
            stand = loc.getWorld().spawn(loc, ArmorStand.class);
        }else{
            stand.teleport(loc);
        }
        return this;
    }

    public boolean isExist(){
        return stand !=  null;
    }

    public ArmorStand getStand(){
        return stand;
    }


    public static ArmorStand getInWorld(Location loc){
        Collection<Entity> collection = loc.getWorld().getNearbyEntities(loc, 1, 1, 1, (t -> t instanceof ArmorStand));
        return (ArmorStand) collection.stream().filter(
                e -> e.getPersistentDataContainer().has(Artilerija.getInstance().getSpreadKey()) &&
                e.getPersistentDataContainer().has(Artilerija.getInstance().getPowerKey())).iterator().next();

    }
    private static ArtGun getFromStand(ArmorStand stand){
        Artilerija plugin = Artilerija.getInstance();
        PersistentDataContainer container = stand.getPersistentDataContainer();

        return new ArtGun(container.get(plugin.getSpreadKey(), PersistentDataType.FLOAT), stand, );
    }

    public void shoot(){
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
        }
    }
    public void point(Location loc){
        Location location = stand.getLocation();
        location.setPitch(loc.getPitch());
        location.setYaw(loc.getYaw());
        stand.teleport(loc);
    }
    public void reload(Projectile projectile1){
        this.projectile = projectile1;
    }
    public void reloadPowder(float charge){
        this.charge = charge;
    }
    public void blowUp(){
        stand.getWorld().createExplosion(stand.getLocation(), 10);
    }


}
