package youat0m.artilerija.nms;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;

public class NMSEnity extends ArmorStand {

    private EntityType<?> type;

    public NMSEnity(Location loc, EntityType<?> type) {
        super(((CraftWorld)loc.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ());
        this.type = type;
    }

    @Override
    public EntityType<?> getType() {
        return type;
    }

    @Override
    public boolean isCollidable(boolean ignoreClimbing) {
        return false;
    }
}
