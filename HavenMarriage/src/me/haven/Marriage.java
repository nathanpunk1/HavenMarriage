package me.haven;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.haven.MarriageCMD;
import me.haven.OnChat;
import me.haven.OnCommand;
import me.haven.OnQuit;
import me.haven.Version;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Marriage extends JavaPlugin{
	public List<String> chat = new ArrayList<String>();
	private List<String> derp = new ArrayList<String>();
	public static Economy economy = null;
	public Logger log = Logger.getLogger("Minecraft");
	public List<String> people = new ArrayList<String>();
	private FileConfiguration customConfig = null;
    private File customConfigFile = null;
	public String name = "Marriage";
	public String version = "1.0";
	public boolean used = false;

	public void onEnable()
	{
		PluginManager pm = getServer().getPluginManager();
		final FileConfiguration config = this.getConfig();

		pm.registerEvents(new OnCommand(this), this);
		pm.registerEvents(new OnQuit(this), this);
		pm.registerEvents(new Version(this), this);
		pm.registerEvents(new OnChat(this), this);

		getCommand("marriage").setExecutor(new MarriageCMD(this));

		config.options().header("Config now supports colors :D!");
		config.addDefault("divorce-message", "&c%player_1% has divorced with %player_2%!");
		config.addDefault("marry-message", "&a%player_1% has married with %player_2%!");
		config.addDefault("settings.double-exp", false);
		config.addDefault("settings.bonus-exp", 0);
		config.addDefault("genders.Join_Message", "%player% joined the game");
		config.addDefault("genders.Quit_Message", "%player% left the game");
		config.addDefault("genders.required", false);
		config.addDefault("money.divorce", 0);
		config.addDefault("money.marry", 0);
		getCustomConfig().addDefault("partners", derp);
		getCustomConfig().addDefault("males", derp);
		getCustomConfig().addDefault("females", derp);
		getCustomConfig().options().copyDefaults(true);
		config.options().copyDefaults(true);
		saveConfig();
		saveCustomConfig();

	    if (setupEconomy().booleanValue())
	    {
	      System.out.println("Marriage has successfully linked with " + economy.getName() + ", via Vault");
	    }
	    else
	    {
	      System.out.println("Marriage: Vault economy not found!");
	    }
		log.info(name + " v" + version + " has been enabled!");
	}
public void reloadCustomConfig() {
    if (customConfigFile == null) {
    customConfigFile = new File(getDataFolder(), "data.yml");
    }
    customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
    java.io.InputStream defConfigStream = this.getResource("data.yml");
    if (defConfigStream != null) {
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        customConfig.setDefaults(defConfig);
    }
}
public FileConfiguration getCustomConfig(){
    if (customConfig == null) {
        this.reloadCustomConfig();
    }
    return customConfig;
}
public void saveCustomConfig() {
    if (customConfig == null || customConfigFile == null) {
    return;
    }
    try {
        getCustomConfig().save(customConfigFile);
    } catch (IOException ex) {
        this.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);}
    }
public boolean hasMoneyDivorce(Player player, double price)
{
	if(economy.getBalance(player.getName()) <= price)
	{
		return false;
	}else
	{
		return true;
	}
}

public boolean buyDivorce(Player player, double price)
{
	if(hasMoneyDivorce(player, price))
	{
		economy.withdrawPlayer(player.getName(), price);
		return true;
	}else
	{
		player.sendMessage("[Marriage] " + ChatColor.RED + "You dont have enough money!");
		return false;
	}
}
public boolean hasMoneyMarry(Player player, double price)
{
	if(economy.getBalance(player.getName()) <= price)
	{
		return false;
	}else
	{
		return true;
	}
}

public boolean buyMarry(Player player, double price)
{
	if(hasMoneyMarry(player, price))
	{
		economy.withdrawPlayer(player.getName(), price);
		return true;
	}else
	{
		player.sendMessage("[Marriage] " + ChatColor.RED + "You dont have enough money!");
		return false;
	}
}

private Boolean setupEconomy()
{
  Plugin vault = getServer().getPluginManager().getPlugin("Vault");
  if (vault == null) {
    return Boolean.valueOf(false);
  }
  @SuppressWarnings("rawtypes")
  RegisteredServiceProvider economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
  if (economyProvider != null) {
    economy = (Economy)economyProvider.getProvider();
  }

  return Boolean.valueOf(economy != null);
}

public String fixColors(String message)
{
	message = message.replaceAll("&0", ChatColor.BLACK + "");
	message = message.replaceAll("&1", ChatColor.DARK_BLUE + "");
	message = message.replaceAll("&2", ChatColor.DARK_GREEN + "");
	message = message.replaceAll("&3", ChatColor.DARK_AQUA + "");
	message = message.replaceAll("&4", ChatColor.DARK_RED + "");
	message = message.replaceAll("&5", ChatColor.DARK_PURPLE + "");
	message = message.replaceAll("&6", ChatColor.GOLD + "");
	message = message.replaceAll("&7", ChatColor.GRAY + "");
	message = message.replaceAll("&8", ChatColor.DARK_GRAY + "");
	message = message.replaceAll("&9", ChatColor.BLUE + "");
	message = message.replaceAll("&a", ChatColor.GREEN + "");
	message = message.replaceAll("&b", ChatColor.AQUA + "");
	message = message.replaceAll("&c", ChatColor.RED + "");
	message = message.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
	message = message.replaceAll("&e", ChatColor.YELLOW + "");
	message = message.replaceAll("&0", ChatColor.WHITE + "");
	return message;
}
}
