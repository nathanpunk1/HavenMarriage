package me.haven;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Version implements Listener{
	private Marriage plugin;
	public Version(Marriage instance) { this.plugin = instance; }

	public String checkForUpdate(String version) throws Exception {
		URL url = new URL("https://raw.github.com/lenis00012/MarryVersion/master/version.version");
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine = in.readLine();
		in.close();

		if (inputLine != null && Double.valueOf(inputLine) <= Double.valueOf(version)) return null;

		return inputLine;
		}

	@EventHandler (priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		if (player != null && (player.isOp() || player.hasPermission("marry.admin"))) {
			plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() { // create a new anonymous task/thread that will check the version asyncronously
				@Override
				public void run() {
					try {
						String oldVersion = "14";
						String newVersion = checkForUpdate(oldVersion);
						if (newVersion != null) // do we have a version update? => notify player
							player.sendMessage("[Marriage] " + ChatColor.YELLOW + "Update avaible, check BukkitDev!");
						if(plugin.used == false)
						{
							player.sendMessage("[Marriage] " + ChatColor.GREEN + "Thanks for using Marriage Reloaded!");
							player.sendMessage("[Marriage] " + ChatColor.GREEN + "If you like ths plugin, please donate at our BukkitDev page!");
							plugin.used = true;
						}
						} catch (Exception e) {
							player.sendMessage("Marriage could not get version update - see log for details.");
							plugin.log.warning("[Marriage] Could not connect to remote server to check for update. Exception said: " + e.getMessage());
						}
					}
				}, 25L);
			}
	}
}