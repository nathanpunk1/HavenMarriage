package me.haven;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChat implements Listener{
	private Marriage plugin;
	public OnChat(Marriage instance) { this.plugin = instance; }

	@EventHandler (priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		String pname = player.getName();
		String message = event.getMessage();

		if(!plugin.people.contains(pname))
		{
			plugin.people.add(pname);
		}
		if(plugin.chat.contains(pname))
		{
			String op = plugin.getCustomConfig().getString("Married." + pname);
			Player oPlayer = null;
			if(plugin.people.contains(op))
			{
				oPlayer = Bukkit.getServer().getPlayer(op);
			}else
			{
				return;
			}
			oPlayer.sendMessage(ChatColor.GREEN + "[Partner] " +ChatColor.RED +  pname + ChatColor.WHITE + ": " + ChatColor.GREEN + message);
			player.sendMessage(ChatColor.GREEN + "[Partner] " +ChatColor.RED +  pname + ChatColor.WHITE + ": " + ChatColor.GREEN + message);
			plugin.log.info("Love Chat: <" + pname + ">: " + message);
			event.setCancelled(true);
		}
	}
}
