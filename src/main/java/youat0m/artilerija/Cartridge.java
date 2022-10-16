package youat0m.artilerija;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

public class Cartridge implements PersistentDataType<byte[], Cartridge>, Serializable {

    public Cartridge(float weight, double power, float charge) {
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
    private double power;
    private float charge;

    private final static Cartridge empty = new Cartridge();

    public static ItemStack create(double power, float weight, float charge){
        ItemStack projectile = new ItemStack(Material.POISONOUS_POTATO);
        ItemMeta meta = projectile.getItemMeta();
        meta.setCustomModelData(715122);
        meta.displayName(Component.text("снаряд").color(NamedTextColor.RED));
        meta.lore(Component.empty().append(Component.text("Сила взрыва: " + power)).append(Component.text("Вес снаряда: " + weight)).append(Component.text("Заряд: "+ charge)).children());
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(plugn.getPowerKey(), PersistentDataType.DOUBLE, power);
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
                    container.get(plugn.getPowerKey(), PersistentDataType.DOUBLE),
                    container.get(plugn.getChargeKey(), PersistentDataType.FLOAT)));
        }else return Optional.empty();
    }

    public float getWeight() {
        return weight;
    }

    public double getPower() {
        return power;
    }

    public float getCharge() {
        return charge;
    }

    public void setWeight(float weight) {
        if(weight == 0)
            throw new IllegalArgumentException("wight can't be 0");
        this.weight = weight;
    }

    public void setPower(double power) {
        if(power < 0)
            throw new IllegalArgumentException("power is less than 0");
        this.power = power;
    }

    public void setCharge(float charge) {
        if(charge < 0)
            throw new IllegalArgumentException("charge is less than 0");
        this.charge = charge;
    }

    public static Cartridge getEmpty() {
        return empty;
    }

    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<Cartridge> getComplexType() {
        return Cartridge.class;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull Cartridge complex, @NotNull PersistentDataAdapterContext context) {
        try {
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream outputStream = new BukkitObjectOutputStream(arrayOutputStream);
            outputStream.writeObject(complex);
            outputStream.close();
            return arrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public @NotNull Cartridge fromPrimitive(@NotNull byte[] primitive, @NotNull PersistentDataAdapterContext context) {
        try {
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(primitive);
            BukkitObjectInputStream inputStream = new BukkitObjectInputStream(arrayInputStream);
            return (Cartridge) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Cartridge(0, 0 ,0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cartridge cartridge = (Cartridge) o;
        return cartridge.charge == charge && cartridge.power == power && cartridge.weight == weight;
    }
}
