package me.haven;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuit implements Listener{
	private Marriage plugin;
	public OnQuit(Marriage instance) { this.plugin = instance; }

	@EventHandler (priority = EventPriority.HIGHEST)
	public void OnPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		String pname = player.getName();

		if(plugin.people.contains(plugin.getCustomConfig().getString("Married." + pname)))
		{
			Player oPlayer = Bukkit.getPlayer(plugin.getCustomConfig().getString("Married." + pname));
			String opname = oPlayer.getName();

			if(plugin.chat.contains(opname))
			{
				plugin.chat.remove(opname);
				oPlayer.sendMessage("[Marriage] " + ChatColor.GREEN + "You have left marry chat mode!");
			}
			if(plugin.chat.contains(pname))
			{
				plugin.chat.remove(pname);
			}
		}
		if(plugin.people.contains(pname))
		{
			plugin.people.remove(pname);
		}
		if(plugin.people.contains(plugin.getCustomConfig().getString("Married." + pname)))
		{
			Player oPlayer = Bukkit.getServer().getPlayer(plugin.getCustomConfig().getString("Married." + pname));
			oPlayer.sendMessage("[Marriage] " + ChatColor.RED + "Your partner is now offline!");
		}
		if(plugin.getCustomConfig().getStringList("males").contains(pname))
		{
			event.setQuitMessage("");
			String message = plugin.getConfig().getString("genders.Quit_Message");
			message = message.replaceAll("%player%", pname);
			message = plugin.fixColors(message);
			plugin.getServer().broadcastMessage(ChatColor.AQUA + message);
		}
		if(plugin.getCustomConfig().getStringList("females").contains(pname))
		{
			event.setQuitMessage("");
			String message = plugin.getConfig().getString("genders.Quit_Message");
			message = message.replaceAll("%player%", pname);
			message = plugin.fixColors(message);
			plugin.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + message);
		}
	}
}