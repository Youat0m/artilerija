package youat0m.artilerija.nms;


import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.jetbrains.annotations.Nullable;

public class NMSEnity extends ArmorStand {

    private EntityType<?> type;
    private Component component;

    public NMSEnity(Location loc, Component component, EntityType<?> type) {
        super(((CraftWorld)loc.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ());
        this.component = component;
        this.type = type;
    }

    @Override
    public EntityType<?> getType() {
        return type;
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return component;
    }

    @Override
    public boolean shouldShowName() {
        return true;
    }
}
