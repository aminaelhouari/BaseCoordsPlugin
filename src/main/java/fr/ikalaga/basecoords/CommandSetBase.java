package fr.ikalaga.basecoords;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;

public class CommandSetBase implements CommandExecutor {
    private final Main plugin;

    public CommandSetBase(Main plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Seul un joueur peut faire cela !");
            return true;
        }
        Player player = (Player) sender;
        Location loc = player.getLocation();
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        if (args.length == 0){
            player.sendMessage("§cErreur : Tu dois donner un nom à ta base idiot");
            return true;
        }
        String nomBase = args[0];
        String path = "bases."+player.getUniqueId()+ "." + nomBase;
        this.plugin.getConfig().set(path + ".x", x);
        this.plugin.getConfig().set(path + ".y", y);
        this.plugin.getConfig().set(path + ".z", z);
        this.plugin.saveConfig();
        player.sendMessage("§aBase " + nomBase + " enregistreee!");
        return true;
    }
}
