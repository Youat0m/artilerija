package youat0m.artilerija;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class SummonManager implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            Artilerija plugin = Artilerija.getInstance();
            ArmorStand stand = p.getWorld().spawn(p.getLocation(), ArmorStand.class);
            PersistentDataContainer container = stand.getPersistentDataContainer();
            container.set(plugin.getPowerKey(), PersistentDataType.DOUBLE, Double.parseDouble(args[0]));
            container.set(plugin.getSpeedKey(), PersistentDataType.FLOAT, Float.parseFloat(args[1]));
            container.set(plugin.getSpreadKey(), PersistentDataType.FLOAT, Float.parseFloat(args[2]));
            stand.customName(Component.text(args[0]).append(Component.text("_").append(Component.text(args[1])).append(Component.text("_")).append(Component.text(args[2]))));
            stand.setCustomNameVisible(true);
        }
        return true;
    }
}
