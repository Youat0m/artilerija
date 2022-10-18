package youat0m.artilerija;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p && p.getTargetEntity(3) != null && p.getTargetEntity(3) instanceof ArmorStand &&
                ArtGun.getFromStand((ArmorStand) p.getTargetEntity(3)).isPresent()){
            Cartridge.getProjectile(p.getInventory().getItemInMainHand()).ifPresent((Cartridge c) -> {
                ArtGun.getFromStand((ArmorStand)p.getTargetEntity(3)).get().reload(c);
                p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
            });
        }else return false;
        return true;
    }
}
