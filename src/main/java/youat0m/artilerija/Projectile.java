package youat0m.artilerija;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public class Projectile {

    public Projectile(float weight, double power) {
        this.weight = weight;
        this.power = power;
    }
    private static Artilerija plugn = Artilerija.getInstance();
    private float weight;
    private double power;

    public static ItemStack create(double power, double weight){
        ItemStack projectile = new ItemStack(Material.POISONOUS_POTATO);
        ItemMeta meta = projectile.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(plugn.getPowerKey(), PersistentDataType.DOUBLE, power);
        container.set(plugn.getWeightKey(), PersistentDataType.DOUBLE, weight);
        projectile.setItemMeta(meta);
        return projectile;
    }
    public static Optional<Projectile> getProjectile(ItemStack stack){
        PersistentDataContainer container = stack.getItemMeta().getPersistentDataContainer();
        if(container.has(plugn.getPowerKey()) &&   container.has(plugn.getWeightKey())){
            return Optional.of(new Projectile(container.get(plugn.getWeightKey(), PersistentDataType.FLOAT),
                    container.get(plugn.getPowerKey(), PersistentDataType.DOUBLE)));
        }else return Optional.empty();
    }

    public float getWeight() {
        return weight;
    }

    public double getPower() {
        return power;
    }
}
