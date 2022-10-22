package youat0m.artilerija;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public interface IArtGun {

    Entity create(Location loc);

    boolean isExist();

    Entity getEntity();

    float getMaxCharge();

    float getSpread();

    default Boolean shoot(){
        if(getCatridge() != null && getCatridge().getPower() != 0){
            if(getCatridge().getCharge() >= getMaxCharge()) blowUp();
            float speed = getCatridge().getCharge() / getCatridge().getWeight();
            Location loc = getEntity().getLocation();
            Arrow arr = loc.getWorld().spawnArrow(loc, loc.getDirection(), speed,
                    getSpread());
            arr.setDamage(getCatridge().getPower());
            arr.setColor(Color.RED);
            arr.setGlowing(true);
            arr.customName(Component.text("atrtelerija"));
            setCartridge(Cartridge.getEmpty());
            if(isExist())
                getEntity().getPersistentDataContainer().set(Artilerija.getInstance().getProjectileKey(), Cartridge.getEmpty(), getCatridge());
            return true;
        }
        return false;
    }

    void point(Location loc);

    void point(float pitch, float yaw);

    void setCartridge(Cartridge cartridge1);

    Cartridge getCatridge();

    default void blowUp(){
        getEntity().getLocation().createExplosion(10);
    }
}
