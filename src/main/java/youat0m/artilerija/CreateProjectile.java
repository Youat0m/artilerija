package youat0m.artilerija;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CreateProjectile implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            p.getInventory().addItem(Cartridge.create(Double.parseDouble(args[0]), Float.parseFloat(args[1]), Float.parseFloat(args[2])));
            return true;
        }
        return false;
    }
}
