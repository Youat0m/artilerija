package youat0m.artilerija;

import org.bukkit.Material;
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
import java.util.Base64;
import java.util.Optional;

public class Projectile implements PersistentDataType<byte[], Projectile>{

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

    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<Projectile> getComplexType() {
        return Projectile.class;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull Projectile complex, @NotNull PersistentDataAdapterContext context) {
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
    public @NotNull Projectile fromPrimitive(@NotNull byte[] primitive, @NotNull PersistentDataAdapterContext context) {
        try {
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(primitive);
            BukkitObjectInputStream inputStream = new BukkitObjectInputStream(arrayInputStream);
            return (Projectile) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Projectile(0);
    }
}
