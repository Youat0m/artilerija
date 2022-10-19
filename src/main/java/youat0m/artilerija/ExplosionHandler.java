package youat0m.artilerija;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataContainer;

public class ExplosionHandler implements Listener {
    @EventHandler
    public void onHit(ProjectileHitEvent e){
        NamespacedKey key = Artilerija.getInstance().getProjectileKey();
        PersistentDataContainer container = e.getEntity().getPersistentDataContainer();
        if(container.has(key)) {
            Location loc;
            if(e.getHitBlock() != null)
                loc = e.getHitBlock().getLocation();
            else loc = e.getHitEntity().getLocation();
            container.get(key, Cartridge.getEmpty()).explode(loc, e.getEntity());
        }
    }
}
