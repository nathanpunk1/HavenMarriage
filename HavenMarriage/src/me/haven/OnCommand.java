package me.haven;


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
		if(message.equals("/marry"))
		{
			player.sendMessage("========{ Marriage Reloaded }=========");
			player.sendMessage("/marry list - see all married players");
			player.sendMessage("/marry <name> - send a marry request to someone");
			player.sendMessage("/marry accept <sender> - accept a marry request");
			player.sendMessage("/marry chat - Chat together with your partner ");
			player.sendMessage("/marry divorce - If things just dont go your way");
			event.setCancelled(true);
		}
	}
}