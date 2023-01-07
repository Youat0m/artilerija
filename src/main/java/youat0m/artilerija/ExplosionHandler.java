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
            Cartridge c = container.get(key, Cartridge.getEmpty());
            if(e.getHitBlock() != null){
                c.explode(e.getEntity(), e.getHitBlock());
            } else
                c.explode(e.getEntity(), e.getHitEntity());
        }
    }
}
