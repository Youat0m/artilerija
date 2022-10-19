package youat0m.artilerija;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

//todo переписать по интрефейс
public class Cartridge implements ICartridge {

    public Cartridge(float weight, float power, float charge) {
        this.weight = weight;
        this.power = power;
        this.charge = charge;
    }

    public Cartridge(){
        this.weight = 0;
        this.power = 0;
        this.charge = 0;
    }

    private static Artilerija plugn = Artilerija.getInstance();
    private float weight;
    private float power;
    private float charge;

    private final static Cartridge empty = new Cartridge();

    public static Cartridge getEmpty(){
        return empty;
    }


    public static ItemStack create(float power, float weight, float charge){
        ItemStack projectile = new ItemStack(Material.POISONOUS_POTATO);
        ItemMeta meta = projectile.getItemMeta();
        meta.setCustomModelData(715122);
        meta.displayName(Component.text("снаряд").color(NamedTextColor.RED));
        meta.lore(Component.empty().append(Component.text("Сила взрыва: " + power)).append(Component.text("Вес снаряда: " + weight)).append(Component.text("Заряд: "+ charge)).children());
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(plugn.getPowerKey(), PersistentDataType.FLOAT, power);
        container.set(plugn.getWeightKey(), PersistentDataType.FLOAT, weight);
        container.set(plugn.getChargeKey(), PersistentDataType.FLOAT, charge);
        projectile.setItemMeta(meta);
        return projectile;
    }

    public static ItemStack create(Cartridge cartridge){
        return create(cartridge.power, cartridge.getWeight(), cartridge.getCharge());
    }

    public static Optional<Cartridge> getProjectile(ItemStack stack){
        if(stack.getItemMeta() == null) return Optional.empty();
        PersistentDataContainer container = stack.getItemMeta().getPersistentDataContainer();
        if(container.has(plugn.getPowerKey()) && container.has(plugn.getWeightKey()) && container.has(plugn.getChargeKey())){
            return Optional.of(new Cartridge(container.get(plugn.getWeightKey(), PersistentDataType.FLOAT),
                    container.get(plugn.getPowerKey(), PersistentDataType.FLOAT),
                    container.get(plugn.getChargeKey(), PersistentDataType.FLOAT)));
        }else return Optional.empty();
    }

    public float getPower() {
        return power;
    }

    @Override
    public float getWeight() {
        return weight;
    }
    
    @Override
    public void setWeight(float weight){
        ICartridge.super.setWeight(weight);
        this.weight = weight;
    }

    @Override
    public float getCharge() {
        return charge;
    }

    public void setPower(float power) {
        if(power < 0)
            throw new IllegalArgumentException("power is less than 0");
        this.power = power;
    }

    @Override
    public void setCharge(float charge) {
        if(charge < 0)
            throw new IllegalArgumentException("charge is less than 0");
        this.charge = charge;
    }

    @Override
    public void explode(Location loc, Entity entity) {
        loc.createExplosion(this.getPower());
        entity.remove();
    }
}
