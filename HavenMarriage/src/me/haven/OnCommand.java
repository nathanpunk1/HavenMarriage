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
		if(message.equals("/marriage"))
		{
			player.sendMessage(ChatColor.RED + "========{ Marriage Reloaded }=========");
			player.sendMessage(ChatColor.RED + "/marriage list - see all married players");
			player.sendMessage(ChatColor.RED + "/marriage <name> - send a marry request to someone");
			player.sendMessage(ChatColor.RED + "/marriage accept <sender> - accept a marry request");
			player.sendMessage(ChatColor.RED + "/marriage chat - Chat together with your partner ");
			player.sendMessage(ChatColor.RED + "/marriage divorce - If things just dont go your way");
			event.setCancelled(true);
		}
	}
}