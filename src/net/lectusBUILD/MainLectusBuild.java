package net.lectusBUILD;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.lectusAPI.grade.Rank;
import net.lectusAPI.utils.TeamUtils;
import net.lectusBUILD.events.EventsManager;
import net.lectusBUILD.thread.SignThread;

public class MainLectusBuild extends JavaPlugin {

	public static MainLectusBuild instance;
	
	public List<Player>					mod					= new ArrayList<>();

	public static MainLectusBuild getInstance() {
		return instance;
	}

	@SuppressWarnings("deprecation")
	public void onEnable() {
		super.onEnable();
		instance = this;
		new EventsManager(this).registerEvents();

		for (Rank rank : Rank.values()) {
			TeamUtils.getInstance().createTeam(rank.getName(), rank.getShortName() + " ");
		}
		
		for (World world : Bukkit.getWorlds()) {
			String name = world.getName();
			getServer().getWorld(name).setStorm(false);
			getServer().getWorld(name).getEntities().clear();
			getServer().getWorld(name).setTime(0);
	          for(Entity en : getServer().getWorld(name).getEntities()){
	              if(!(en instanceof Player)) {
	              en.remove();
	              }
	          }
		}

		Bukkit.getScheduler().runTaskTimer(this, new BukkitRunnable() {
			@Override
			public void run() {
				SignThread.signReload();
			}
		}, 0, 40);
	}

	public void onDisable() {
		super.onDisable();
		for (Rank rank : Rank.values()) {
			TeamUtils.getInstance().deleteTeam(rank.getName());
		}
	}
}