package youat0m.artilerija;

import org.bukkit.NamespacedKey;
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

    @Override
    public void onEnable() {
        getCommand("shoot").setExecutor(new ShootExecutor());
        getCommand("summonArt").setExecutor(new SummonManager());
        getServer().getPluginManager().registerEvents(new ExplosionHandler(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Artilerija getInstance() {
        return instanse;
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

    public NamespacedKey getWeightKey() {
        return weightKey;
    }
}