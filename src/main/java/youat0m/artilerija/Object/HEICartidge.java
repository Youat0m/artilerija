package youat0m.artilerija.Object;

import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;

public class HEICartidge extends Cartridge implements ICartridge{
    @Override
    public void explode(Projectile projectile, Entity target) {
        if(target instanceof Damageable d) d.damage(getWeight()*5);
        explode(projectile, target.getLocation().getBlock());
    }

    @Override
    public void explode(Projectile projectile, Block target) {
        target.getLocation().createExplosion(this.getPower()/2);
        target.getLocation().createExplosion(this.getPower()*2, false, false);
        projectile.remove();
    }
}
