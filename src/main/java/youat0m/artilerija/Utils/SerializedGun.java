package youat0m.artilerija.Utils;

import org.bukkit.Location;
import youat0m.artilerija.Object.ArtGunStand;
import youat0m.artilerija.Artilerija;

import java.io.Serializable;

public record SerializedGun(ArtGunStand stand, String world, double x, double y, double z, String type) implements Serializable {

    public SerializedGun(ArtGunStand artGunStand){
        this(artGunStand, artGunStand.getEntity().getLocation().getWorld().getName(), artGunStand.getEntity().getLocation().getX(), artGunStand.getEntity().getLocation().getY(), artGunStand.getEntity().getLocation().getZ(), artGunStand.getType().id);
    }

    public void create(){
        stand.create(new Location(Artilerija.getInstance().getServer().getWorld(world), x, y, z), type);
    }

}
