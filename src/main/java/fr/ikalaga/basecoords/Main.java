package fr.ikalaga.basecoords;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Le plugin BaseCoords est chargé");
        getCommand("setbase").setExecutor(new CommandSetBase(this));
        getCommand("base").setExecutor(new CommandBase(this));
    }
}