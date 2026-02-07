package fr.ikalaga.basecoords;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.configuration.ConfigurationSection;
import java.util.ArrayList;
import java.util.List;

public class CommandBase implements CommandExecutor, TabCompleter {
    private final Main plugin;

    public CommandBase(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Seul un joueur peut faire cela !");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0){
            player.sendMessage("Tu dois donner le nom d'une base (/base [nom]");
            return true;
        }
        String nomBase = args[0];
        String path = "bases."+player.getUniqueId()+ "." + nomBase;
        if (this.plugin.getConfig().contains(path)) {
            double x = this.plugin.getConfig().getDouble(path + ".x");
            double y = this.plugin.getConfig().getDouble(path + ".y");
            double z = this.plugin.getConfig().getDouble(path + ".z");
            player.sendMessage("§a[BaseCoords] §e" + nomBase + " se trouve en : §f" + (int)x + ", " + (int)y + ", " + (int)z);
        } else {
            player.sendMessage("§cErreur : La base '" + nomBase + "' n'existe pas frérot");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) return new ArrayList<>();
        Player player = (Player) sender;

        if (args.length == 1) {
            ConfigurationSection section = this.plugin.getConfig().getConfigurationSection("bases." + player.getUniqueId());
            if (section != null) {
                return new ArrayList<>(section.getKeys(false));
            }
        }
        return new ArrayList<>();
    }
}
