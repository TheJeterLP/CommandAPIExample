package de.thejeterlp.commandapiexample;

import de.thejeterlp.commandapi.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
    
    private static Main INSTANCE;
    
    @Override
    public void onEnable() {
        INSTANCE = this;
        
        //Create new instance of the CommandManager class used to load the command classes
        CommandManager mgr = new CommandManager(this);
        
        //Tell Bukkit to use the ExampleCommands as Executor
        mgr.registerClass(ExampleCommands.class);
    }
    
    @Override
    public void onDisable() {
        //do nothing
    }
    
    public static Main getInstance() {
        return INSTANCE;
    }
    
}
