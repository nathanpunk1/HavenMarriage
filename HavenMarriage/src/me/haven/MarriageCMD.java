package me.haven;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class MarriageCMD implements CommandExecutor{
	public List<String> reqs = new ArrayList<String>();
	public List<String> partners = new ArrayList<String>();
	private Marriage plugin;
	public MarriageCMD(Marriage instance) { this.plugin = instance; }
	private String NoPerm = "You dont have Permission!";
	private String Marry = "You Are Now Married Congratulations";
	private String po = "Your partner is offline!";
	
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args)
	{
		Player player = null;

	    if ((sender instanceof Player))
	    {
	      player = (Player)sender;
	    } else {
	    	sender.sendMessage(ChatColor.RED + "Sorry, but you're alone.");
	    }


		File file = new File(plugin.getDataFolder(), "data.yml");
		if(file.exists())
		{
			partners = plugin.getCustomConfig().getStringList("partners");
		}

	    if(player.hasPermission("love.*"))
	    {
	    	player.hasPermission("love.cost");
	    	player.hasPermission("love.marry");
	    	player.hasPermission("love.love");
	    	player.hasPermission("love.list");
	    	player.hasPermission("love.chat");
	    }

	    if(args[0].equals("accept"))
	    {
	    	if(args.length == 2)
	    	{
	    		if(!player.hasPermission("love.marry"))
	    		{
	    			player.sendMessage(ChatColor.RED + this.NoPerm);
	    			return true;
	    		}
	    		Player oPlayer = null;
	    		if(plugin.people.contains(args[1]))
	    		{
	    			oPlayer = Bukkit.getServer().getPlayer(args[1]); 
	    		}else
	    		{
	    			player.sendMessage(ChatColor.RED + "That player does not exist!");
	    			return true;
	    		}
	    		if(args[1] == player.getName())
	    		{
	    			player.sendMessage(ChatColor.RED + "You may not marry yourself!");
	    			return true;
	    		}else
	    		this.Accept(player, oPlayer);
	    	}else
	    	{
	    		player.sendMessage("Invalid useage: /love accept <sender>");
	    	}
	    }

	    else if(args[0].equals("cost"))
	    {
    		if(!player.hasPermission("love.cost"))
    		{
    			player.sendMessage(ChatColor.RED + this.NoPerm);
    			return true;
    		}
	    	this.Cost(player);
	    }

	    
	    else if(args[0].equals("list"))
	    {
    		if(!player.hasPermission("love.list"))
    		{
    			player.sendMessage(ChatColor.RED + this.NoPerm);
    			return true;
    		}
	    	this.showList(player);
	    }

	    else if(args[0].equals("love"))
	    {
	    	if(!player.hasPermission("love.love"))
    		{
    			player.sendMessage(ChatColor.RED + this.NoPerm);
    			return true;
    		}
	    	if(plugin.people.contains(plugin.getCustomConfig().getString("Married." + player.getName())))
	    	{
	    		Bukkit.getServer().getPlayer(plugin.getCustomConfig().getString("Married." + player.getName()));
	    	}else
	    	{
	    		player.sendMessage(ChatColor.RED + this.po);
	    		return true;
	    	}
	    }
	    
	    else if(args[0].equals("divorce"))
	    {
    		String opname = plugin.getCustomConfig().getString("Married." + player.getName());
    		if(opname == "" || opname == null)
    		{
    			player.sendMessage(ChatColor.RED + "You are not married!");
    		}
    		this.divorce(player, opname);
	    }

	    
	    else if(args[0].equals("chat"))
	    {
	    	if(!player.hasPermission("love.chat"))
    		{
    			player.sendMessage(ChatColor.RED + this.NoPerm);
    			return true;
    		}
	    	if(!plugin.people.contains(plugin.getCustomConfig().getString("Married." + player.getName())))
	    	{
	    		player.sendMessage(ChatColor.RED + this.po);
	    		return true;
	    	}
	    	this.chat(player);
	    }
		else if(args.length == 1)
		{
			if(args[0].equals(sender.getName()))
			{
				sender.sendMessage(ChatColor.RED + "You may not marry yourself!");
				return true;
			}
    		if(!player.hasPermission("love.marry"))
    		{
    			player.sendMessage(ChatColor.RED + this.NoPerm);
    			return true;
    		}
			Player oPlayer = null;
			if(plugin.people.contains(args[0]))
			{
				oPlayer = Bukkit.getServer().getPlayer(args[0]);
			}else
			{
				player.sendMessage(ChatColor.RED + "That player does not exist!");
				return true;
			}
			this.SendRequest(player, oPlayer);
		}
		return true;
	}
	
	public void SendRequest(Player player, Player oPlayer)
	{
		String pname = player.getName();
		String opname = oPlayer.getName();

		if(plugin.getCustomConfig().getString("Married." + pname).equalsIgnoreCase("Married." + pname) && plugin.getCustomConfig().getString("Married." + pname) != null && plugin.getCustomConfig().getString("Married." + pname) != "")
		{
			player.sendMessage(ChatColor.RED + "You are already Married!");
			return;
		}
		if(plugin.getCustomConfig().getString("Married." + opname).equalsIgnoreCase("Married." + opname) && plugin.getCustomConfig().getString("Married." + opname) != null && plugin.getCustomConfig().getString("Married." + opname) != "")
		{
			player.sendMessage(ChatColor.RED + opname + " is already Married!");
			return;
		}
		if(plugin.getConfig().getInt("money.marry") != 0)
		{
			if(plugin.buyMarry(player, plugin.getConfig().getInt("money.marry")))
			{
				player.sendMessage(ChatColor.GREEN + "$" + plugin.getConfig().getInt("money.marriage") + " has been taken form your balance!");
			}else
			{
				return;
			}
		}
		player.sendMessage(ChatColor.GREEN + "Request has been sent!");
		oPlayer.sendMessage(ChatColor.GREEN + pname + " requested you to marry, type: " + ChatColor.LIGHT_PURPLE + "/love accept <sender>" + ChatColor.GREEN + " to accept");
		reqs.add(opname);
		reqs.add(pname);
	}

	public void Accept(Player player, Player oPlayer)
	{
	
		String pname = player.getName();
		String opname = oPlayer.getName();
		if(plugin.getCustomConfig().getString("Married." + pname).equalsIgnoreCase("Married." + pname) && plugin.getCustomConfig().getString("Married." + pname) != null && plugin.getCustomConfig().getString("Married." + pname) != "")
		{
			player.sendMessage(ChatColor.RED + "You are already Married!");
			return;
		}
		if(plugin.getCustomConfig().getString("Married." + opname).equalsIgnoreCase("Married." + opname) && plugin.getCustomConfig().getString("Married." + opname) != null && plugin.getCustomConfig().getString("Married." + opname) != "")
		{
			player.sendMessage(ChatColor.RED + opname + "is already Married!");
			return;
		}
		if(!reqs.contains(pname))
		{
			player.sendMessage(ChatColor.RED + "You dont have a marry request!");
			return;
		}
		if(!reqs.contains(opname))
		{
			player.sendMessage(ChatColor.RED + "You dont have a marry request from that player!");
			return;
		}
		plugin.getCustomConfig().set("Married." + pname, opname);
		plugin.getCustomConfig().set("Married." + opname, pname);
		plugin.getCustomConfig().set("partners", partners);
		plugin.saveCustomConfig();
		player.sendMessage(ChatColor.GREEN + this.Marry + " " + opname);
		oPlayer.sendMessage(ChatColor.GREEN + this.Marry + " " + pname);
		String message = plugin.getConfig().getString("marry-message");
		message = message.replaceAll("%player_1%", pname);
		message = message.replaceAll("%player_2%", opname);
		message = plugin.fixColors(message);
		Bukkit.getServer().broadcastMessage(message);
	}

	public void Cost(Player player)
	{	
		player.sendMessage(ChatColor.GREEN + "Marriage:" + "$" + plugin.getConfig().getInt("money.marriage"));
		player.sendMessage(ChatColor.GREEN + "Divorce:" + "$" + plugin.getConfig().getInt("money.divorce"));
	}
	
	public void showList(Player player)
	{	
		player.sendMessage(ChatColor.RED + "========= Married Couples =========");

		for(String partner : plugin.getCustomConfig().getStringList("partners"))
		{
			player.sendMessage(partner + " + " + plugin.getCustomConfig().getString("Married." + partner));
		}
	}

	public void divorce(Player player, String opname)
	{
		if(plugin.getConfig().getInt("money.divorce") != 0)
		{
			if(plugin.buyMarry(player, plugin.getConfig().getInt("money.divorce")))
			{
				player.sendMessage(ChatColor.GREEN + "$" + plugin.getConfig().getInt("money.divorce") + " has been taken form your balance!");
			}else
			{
				return;
			}
			plugin.getConfig().getInt("money.divorce");
		}
		String pname = player.getName();
		plugin.getCustomConfig().set("Married." + pname, "");
		plugin.getCustomConfig().set("Married." + opname, "");
		plugin.saveCustomConfig();
		if(partners.contains(opname))
		{
			partners.remove(opname);
			plugin.getCustomConfig().set("partners", partners);
			plugin.saveCustomConfig();
		}
		if(partners.contains(pname))
		{
			partners.remove(pname);
			plugin.getCustomConfig().set("partners", partners);
			plugin.saveCustomConfig();
		}
		player.sendMessage(ChatColor.GREEN + "You have divorced your partner");
		String div = plugin.getConfig().getString("divorce-message");
		div = div.replaceAll("%player_1%", pname);
		div = div.replaceAll("%player_2%", opname);
		div = plugin.fixColors(div);
		Bukkit.getServer().broadcastMessage(div);
	}


	public void chat(Player player)
	{
		String pname = player.getName();
		if(!plugin.chat.contains(pname))
		{
			plugin.chat.add(pname);
			player.sendMessage(ChatColor.GREEN + "You have entered marry chat mode!");
		}
		else if(plugin.chat.contains(pname))
		{
			plugin.chat.remove(pname);
			player.sendMessage(ChatColor.GREEN + "You have left marry chat mode!");
		}else
		{
			plugin.chat.add(pname);
			player.sendMessage(ChatColor.GREEN + "You have entered marry chat mode!");
		}
	}

}
