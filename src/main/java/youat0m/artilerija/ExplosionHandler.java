package youat0m.artilerija;

import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ExplosionHandler implements Listener {
    @EventHandler
    public void onHit(ProjectileHitEvent e){
        if(e.getEntity().customName().equals(Component.text("atrtelerija"))) {
            float power = (float) ((Arrow)e.getEntity()).getDamage();
            if (e.getHitBlock() != null) {
                Block b = e.getHitBlock();
                b.getWorld().createExplosion(b.getLocation(), power);
            } else {
                Entity ent = e.getHitEntity();
                ent.getWorld().createExplosion(ent.getLocation(), power);
            }
            e.getEntity().remove();
        }
    }
}
