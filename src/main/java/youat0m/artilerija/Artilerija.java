package youat0m.artilerija;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import youat0m.artilerija.Utils.SerializedGun;

import java.io.*;
import java.util.ArrayList;

public final class Artilerija extends JavaPlugin {

    private static Artilerija instanse;
    private File file = new File(getDataFolder() + File.separator + "arta.bin");

    public Artilerija(){
        instanse = this;
    }

    private final NamespacedKey spreadKey = new NamespacedKey(this, "spread");
    private final NamespacedKey powerKey = new NamespacedKey(this, "power");
    private final NamespacedKey weightKey = new NamespacedKey(this, "weight");
    private final NamespacedKey maxChargeKey = new NamespacedKey(this, "maxCharge");
    private final NamespacedKey chargeKey = new NamespacedKey(this, "charge");
    private final NamespacedKey projectileKey = new NamespacedKey(this, "projectile");


    @Override
    public void onEnable() {

        getLogger().info("шалом");
        saveDefaultConfig();


        if (file.exists()) {
            try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file)))
            {
                ((ArrayList<SerializedGun>)inputStream.readObject()).forEach(SerializedGun::create);
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
            catch (ClassNotFoundException e){
                getLogger().warning("файл arta.bin повреждён");
                e.printStackTrace();
            }
        }else
            getLogger().info("запись о установках отсутствует");

        getServer().getPluginManager().registerEvents(new ExplosionHandler(), this);
        if(getConfig().getBoolean("dummyPlugin.commands")) {
            getCommand("shoot").setExecutor(new ShootExecutor());
            getCommand("summonArt").setExecutor(new SummonManager());
            getCommand("createProjectile").setExecutor(new CreateProjectile());
            getCommand("reloadGun").setExecutor(new ReloadExecutor());
            getCommand("pointGun").setExecutor(new PointExecutor());
        }
        if(getConfig().getBoolean("dummyPlugin.crafts")){
            registerCrafts();
        }
    }

    private void registerCrafts() {
        ShapedRecipe cartridgeResipe = new ShapedRecipe(new NamespacedKey(this, "cartridge"),
                Cartridge.createItem(5, 5, 15));
        cartridgeResipe.shape(" 0 ", "ipi", "ppp");
        cartridgeResipe.setIngredient('p', Material.GUNPOWDER);
        cartridgeResipe.setIngredient('0', Material.TNT);
        cartridgeResipe.setIngredient('i', Material.IRON_INGOT);

        Bukkit.addRecipe(cartridgeResipe);
    }

    @Override
    public void onDisable() {
        saveConfig();
        try {
            Bukkit.removeRecipe(new NamespacedKey(this, "cartridge"));
        } catch (Exception ignored) {

        }


        ArrayList<SerializedGun> list = new ArrayList<>();
        getServer().getWorld("world").getEntities().forEach(entity -> {
            if (ArtGunStand.check(entity)) {

                list.add(new SerializedGun(new ArtGunStand(entity)));
                entity.remove();
            }
        });

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(list);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
    public static Artilerija getInstance() {
        return instanse;
    }

    public NamespacedKey getChargeKey() {
        return chargeKey;
    }

    public NamespacedKey getMaxChargeKey() {
        return maxChargeKey;
    }

    public NamespacedKey getSpreadKey() {
        return spreadKey;
    }

    public NamespacedKey getPowerKey() {
        return powerKey;
    }

    public NamespacedKey getProjectileKey() {
        return projectileKey;
    }

    public NamespacedKey getWeightKey() {
        return weightKey;
    }
}
