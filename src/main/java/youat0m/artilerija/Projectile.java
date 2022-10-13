package youat0m.artilerija;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Projectile implements PersistentDataType{

    public Projectile(double power) {
        this.power = power;
        this.weight = (float) (power*10);
    }
    private static Artilerija plugn = Artilerija.getInstance();
    private float weight;
    private double power;

    public static ItemStack create(double power, double weight){
        ItemStack projectile = new ItemStack(Material.POISONOUS_POTATO);
        ItemMeta meta = projectile.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(plugn.getPowerKey(), PersistentDataType.DOUBLE, power);
        projectile.setItemMeta(meta);
        return projectile;
    }
    public static Optional<Projectile> getProjectile(ItemStack stack){
        PersistentDataContainer container = stack.getItemMeta().getPersistentDataContainer();
        if(container.has(plugn.getPowerKey())){
            return Optional.of(new Projectile(container.get(plugn.getPowerKey(), PersistentDataType.DOUBLE)));
        }else return Optional.empty();
    }

    public float getWeight() {
        return weight;
    }

    public double getPower() {
        return power;
    }

    //todo доделать DataType
    @Override
    public @NotNull Class getPrimitiveType() {
        return null;
    }

    @Override
    public @NotNull Class getComplexType() {
        return null;
    }

    @NotNull
    @Override
    public Object toPrimitive(@NotNull Object complex, @NotNull PersistentDataAdapterContext context) {
        return null;
    }

    @NotNull
    @Override
    public Object fromPrimitive(@NotNull Object primitive, @NotNull PersistentDataAdapterContext context) {
        return null;
    }
}
