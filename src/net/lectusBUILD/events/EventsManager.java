package net.lectusBUILD.events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import net.lectusBUILD.MainLectusBuild;

public class EventsManager {
	
	public MainLectusBuild pl;
	
	public EventsManager(MainLectusBuild MainLectusHub) {
		this.pl = MainLectusHub;
	}
	
	public void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new PlayerEvents(), pl);
		pm.registerEvents(new JoinEvent(), pl);
		pm.registerEvents(new WeatherEvent() , pl);
	}
	
}
