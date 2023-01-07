package youat0m.artilerija;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SummonManager implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            ArtGunStand gun = new ArtGunStand(Float.parseFloat(args[0]), Float.parseFloat(args[1]), args[2]);
            gun.create(p.getLocation());
            return true;
        }
        return false;
    }
}
