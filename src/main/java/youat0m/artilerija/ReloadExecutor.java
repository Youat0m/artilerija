package youat0m.artilerija;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p && p.getTargetEntity(3) != null &&
                ArtGunStand.check(p.getTargetEntity(3))){
            Cartridge.getProjectileFromItem(p.getInventory().getItemInMainHand()).ifPresent((Cartridge c) -> {
                new ArtGunStand(p.getTargetEntity(3)).reload(c);
                p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
            });
        }else return false;
        return true;
    }
}
