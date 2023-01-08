package youat0m.artilerija;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import youat0m.artilerija.Object.Cartridge;
import youat0m.artilerija.Object.HEICartidge;

public class CreateProjectile implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            if(args.length > 3 && args[3].equals("HEI")){
                p.getInventory().addItem(HEICartidge.createItem(Float.parseFloat(args[0]), Float.parseFloat(args[1]), Float.parseFloat(args[2])));
                return true;
            }
            p.getInventory().addItem(Cartridge.createItem(Float.parseFloat(args[0]), Float.parseFloat(args[1]), Float.parseFloat(args[2])));
            return true;
        }
        return false;
    }
}
