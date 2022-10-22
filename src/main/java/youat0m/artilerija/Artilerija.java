package youat0m.artilerija;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class Artilerija extends JavaPlugin {

    private static Artilerija instanse;

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

        saveDefaultConfig();

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
        ShapedRecipe catridgeResipe = new ShapedRecipe(new NamespacedKey(this, "catridge"),
                Cartridge.create(5, 5, 15));
        catridgeResipe.setIngredient('P', Material.GUNPOWDER);
        catridgeResipe.setIngredient('0', Material.TNT);
        catridgeResipe.setIngredient('I', Material.IRON_INGOT);
        catridgeResipe.shape(" 0 ", "IPI", "PPP");

        Bukkit.addRecipe(catridgeResipe);
    }

    @Override
    public void onDisable() {
        saveConfig();
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
