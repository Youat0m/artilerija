package youat0m.artilerija;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

//todo переписать под интерфейс
public class ArtGunStand implements IArtGun {

    private final static Artilerija plugin = Artilerija.getInstance();

    private final float maxCharge;
    private final float spread;
    private Cartridge cartridge = Cartridge.getEmpty();
    private ArmorStand stand;

    public ArtGunStand(ArmorStand stand, float spread, float maxCharge, Cartridge cartridge) {
        this.spread = spread;
        this.stand = stand;
        this.maxCharge = maxCharge;
        this.cartridge = cartridge;
    }

    public ArtGunStand(float maxCharge, float spread) {
        this.maxCharge = maxCharge;
        this.spread = spread;
    }

    @Override
    public Entity create(Location loc){
        if(stand ==  null) {
            stand = loc.getWorld().spawn(loc, ArmorStand.class);
        }else{
            stand.teleport(loc);
        }
        PersistentDataContainer container = stand.getPersistentDataContainer();
        container.set(plugin.getSpreadKey(), PersistentDataType.FLOAT, spread);
        container.set(plugin.getMaxChargeKey(), PersistentDataType.FLOAT, maxCharge);
        container.set(plugin.getProjectileKey(), Cartridge.getEmpty(), cartridge);
        return this.stand;
    }

    static public Optional<ArtGunStand> getFromStand(ArmorStand stand){
        PersistentDataContainer container = stand.getPersistentDataContainer();
        if(container.has(ArtGunStand.plugin.getMaxChargeKey()) &&
                container.has(ArtGunStand.plugin.getSpreadKey()) &&
                container.has(ArtGunStand.plugin.getProjectileKey()))
            return Optional.of(new ArtGunStand(stand,
                    container.get(ArtGunStand.plugin.getSpreadKey(), PersistentDataType.FLOAT),
                    container.get(ArtGunStand.plugin.getMaxChargeKey(), PersistentDataType.FLOAT),
                    container.get(ArtGunStand.plugin.getProjectileKey(), Cartridge.getEmpty())));
        return Optional.empty();
    }

    @Override
    public boolean isExist(){
        return stand != null;
    }

    @Override
    public ArmorStand getEntity(){
        return stand;
    }

    @Override
    public float getMaxCharge() {
        return this.maxCharge;
    }

    @Override
    public float getSpread() {
        return this.spread;
    }

    @Override
    public Boolean shoot(){
        if(cartridge != null && cartridge.getPower() != 0){
            if(cartridge.getCharge() >= maxCharge) blowUp();
            float speed = cartridge.getCharge() / cartridge.getWeight();
            Location loc = stand.getEyeLocation();
            Arrow arr = loc.getWorld().spawnArrow(loc, loc.getDirection(), speed,
                    this.spread);
            arr.setDamage(cartridge.getPower());
            arr.setColor(Color.RED);
            arr.setGlowing(true);
            arr.customName(Component.text("atrtelerija"));
            cartridge = Cartridge.getEmpty();
            if(isExist())
                stand.getPersistentDataContainer().set(plugin.getProjectileKey(), Cartridge.getEmpty(), cartridge);
            return true;
        }
        return false;
    }
    @Override
    public void point(Location loc){
        point(loc.getPitch(), loc.getYaw());
    }

    @Override
    public void point(float pitch, float yaw){
        if(stand != null){
            Location loc = stand.getLocation();
            loc.setPitch(pitch);
            loc.setYaw(yaw);
            stand.teleport(loc);
        }
    }

    @Override
    public void setCartridge(Cartridge cartridge1) {

    }

    @Override
    public Cartridge getCatridge() {
        return null;
    }

    public void reload(Cartridge cartridge1){
        if(cartridge1.getPower()==0 && cartridge1.getWeight() == 0 )
            this.cartridge.setCharge(cartridge1.getCharge());
        else if(!cartridge.equals(Cartridge.getEmpty())) {
            stand.getWorld().dropItem(stand.getLocation(), Cartridge.create(cartridge));
            this.cartridge = cartridge1;
        }else
            this.cartridge = cartridge1;
        if (isExist())
            stand.getPersistentDataContainer().set(plugin.getProjectileKey(), Cartridge.getEmpty(), cartridge);
    }


}
