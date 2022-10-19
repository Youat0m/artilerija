package youat0m.artilerija;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public interface IArtGun {

    Entity create(Location loc);

    boolean isExist();

    Entity getEntity();

    Boolean shoot();

    void point(Location loc);

    void point(float pitch, float yaw);

    void reload(Cartridge cartridge1);

    void blowUp();
}
