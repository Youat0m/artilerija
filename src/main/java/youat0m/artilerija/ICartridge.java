package youat0m.artilerija;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public interface ICartridge extends PersistentDataType<byte[], Cartridge>, Serializable {

    float getWeight();

    float getCharge();

    default void setWeight(float weight) {
        if (weight == 0)
            throw new IllegalArgumentException("wight can't be 0");
    }

    void setCharge(float charge);

    @Override
    @NotNull
    default Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    @NotNull
    default Class<Cartridge> getComplexType() {
        return Cartridge.class;
    }

    @Override
    default byte @NotNull [] toPrimitive(@NotNull Cartridge complex, @NotNull PersistentDataAdapterContext context) {
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
    @NotNull
    default Cartridge fromPrimitive(@NotNull byte[] primitive, @NotNull PersistentDataAdapterContext context) {
        try {
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(primitive);
            BukkitObjectInputStream inputStream = new BukkitObjectInputStream(arrayInputStream);
            return (Cartridge) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Cartridge(0, 0, 0);
    }

    boolean equals(Object o);

    void explode(Location loc, Entity entity);
}
