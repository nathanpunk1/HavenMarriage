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
			player.sendMessage(ChatColor.RED + "/love list" + ChatColor.GREEN + "- see all married players");
			player.sendMessage(ChatColor.RED + "/love <name>" + ChatColor.GREEN + "- send a marry request to someone");
			player.sendMessage(ChatColor.RED + "/love accept <sender>" + ChatColor.GREEN +  "- accept a marry request");
			player.sendMessage(ChatColor.RED + "/love chat" + ChatColor.GREEN + "- Enter/Leave Love Chat ");
			player.sendMessage(ChatColor.RED + "/love divorce" + ChatColor.GREEN +  "- Divorce You Partner");
			event.setCancelled(true);
		}
	}
}