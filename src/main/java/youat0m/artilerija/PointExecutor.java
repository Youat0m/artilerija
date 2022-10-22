package youat0m.artilerija;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PointExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length >= 2 && sender instanceof Player p && ArtGunStand.getFromStand((ArmorStand) p.getTargetEntity(3)).isPresent()){
            IArtGun gun = ArtGunStand.getFromStand((ArmorStand) p.getTargetEntity(3)).get();
            try {
                gun.point(Float.parseFloat(args[0]), Float.parseFloat(args[1]));
            }catch (NumberFormatException e){
                p.sendMessage("числа указаны не верно");
                p.sendMessage(e.getMessage());
            }
        }
        return false;
    }
}