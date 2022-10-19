package youat0m.artilerija;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShootExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            if(p.getTargetEntity(3) instanceof ArmorStand stand && ArtGunStand.getFromStand(stand).isPresent()){
                if(!ArtGunStand.getFromStand(stand).get().shoot())
                    p.sendMessage("нет заряда");
            }else
                p.sendMessage("смотри на пушку, гений");
        }
        return false;
    }
}
