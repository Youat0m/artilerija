package youat0m.artilerija;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class ShootExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            Artilerija plugin = Artilerija.getInstance();
            if(p.getTargetEntity(3) instanceof ArmorStand stand && !ArtGun.getFromStand(stand).isEmpty()){
                if(!ArtGun.getFromStand(stand).orElseThrow().shoot())
                    p.sendMessage("нет заряда");
            }else
                p.sendMessage("смотри на пушку, гений");
        }
        return false;
    }
}
