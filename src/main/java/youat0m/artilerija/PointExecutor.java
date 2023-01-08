package youat0m.artilerija;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import youat0m.artilerija.Object.ArtGunStand;

public class PointExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length >= 2 && sender instanceof Player p && p.getTargetEntity(3) != null &&
                ArtGunStand.check(p.getTargetEntity(3))){
            ArtGunStand gun = new ArtGunStand(p.getTargetEntity(3));
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
