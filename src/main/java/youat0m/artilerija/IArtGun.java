package youat0m.artilerija;

import net.kyori.adventure.text.Component;
import net.minecraft.world.entity.EntityType;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.io.Serializable;

public interface IArtGun extends Serializable {

    Entity create(Location loc);

    boolean isExist();

    Entity getEntity();

    float getMaxCharge();

    float getSpread();

    default Boolean shoot(){
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

    void point(Location loc);

    void point(float pitch, float yaw);

    void setCartridge(Cartridge cartridge1);

    EntityType<?> getType();

    Cartridge getCartridge();

    default void blowUp(){
        getEntity().getLocation().createExplosion(10);
    }
}
