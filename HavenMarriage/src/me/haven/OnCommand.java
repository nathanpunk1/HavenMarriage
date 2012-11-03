package me.haven;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class OnCommand implements Listener{
	private Marriage plugin;
	public OnCommand(Marriage instance) { this.plugin = instance; }

	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
	{
		Player player = event.getPlayer();
		String pname = player.getName();
		String message = event.getMessage();

		if(!plugin.people.contains(pname))
		{
			plugin.people.add(pname);
		}
		if(message.equals("/love"))
		{
			player.sendMessage(ChatColor.RED + "========{ LoveLife }=========");
			player.sendMessage(ChatColor.RED + "/love list" + ChatColor.GREEN + " - See All Married Players");
			player.sendMessage(ChatColor.RED + "/love <name>" + ChatColor.GREEN + " - Send a Marry Request To Someone");
			player.sendMessage(ChatColor.RED + "/love accept <sender>" + ChatColor.GREEN +  " - Accept a Marry Request");
			player.sendMessage(ChatColor.RED + "/love chat" + ChatColor.GREEN + " - Enter/Leave Love Chat ");
			player.sendMessage(ChatColor.RED + "/love divorce" + ChatColor.GREEN +  " - Divorce You Partner");
			player.sendMessage(ChatColor.RED + "/love cost" + ChatColor.GREEN +  " - The Cost Of Marriage/Divorce");
			event.setCancelled(true);
		}
	}
}