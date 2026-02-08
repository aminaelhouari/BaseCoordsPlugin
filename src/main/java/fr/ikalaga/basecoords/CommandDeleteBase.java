package fr.ikalaga.basecoords;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandDeleteBase implements CommandExecutor, TabCompleter {
    private final Main plugin;

    public CommandDeleteBase(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 1. Vérification : est-ce un joueur ?
        if (!(sender instanceof Player)) {
            sender.sendMessage("Seul un joueur peut faire cela !");
            return true;
        }

        Player player = (Player) sender;

        // 2. Vérification : est-ce qu'il y a un argument ?
        if (args.length == 0) {
            player.sendMessage("§cErreur : Tu dois écrire le nom d'une base (/deletebase [nom])");
            return true;
        }

        String nomBase = args[0];
        String path = "bases." + player.getUniqueId() + "." + nomBase;

        // 3. Suppression si la base existe
        if (plugin.getConfig().contains(path)) {
            plugin.getConfig().set(path, null); // On supprime la clé dans la config
            plugin.saveConfig(); // On sauvegarde les changements
            player.sendMessage("§a[Base] La base §e" + nomBase + " §aa été supprimée avec succès !");
        } else {
            player.sendMessage("§cErreur : La base '" + nomBase + "' n'existe pas frérot.");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) return new ArrayList<>();
        Player player = (Player) sender;

        if (args.length == 1) {
            // On récupère la liste des bases du joueur pour l'auto-complétion
            ConfigurationSection section = this.plugin.getConfig().getConfigurationSection("bases." + player.getUniqueId());
            if (section != null) {
                return new ArrayList<>(section.getKeys(false));
            }
        }
        return new ArrayList<>();
    }
}