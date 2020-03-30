package net.lectusBUILD.events;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import net.lectusBUILD.MainLectusBuild;

public class WeatherEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onWeatherChange(WeatherChangeEvent event) {

		boolean rain = event.toWeatherState();
		if (rain) {
			event.setCancelled(true);
			for(World w : MainLectusBuild.getInstance().getServer().getWorlds()) {
				w.setStorm(false);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onThunderChange(ThunderChangeEvent event) {

		boolean storm = event.toThunderState();
		if (storm) {
			event.setCancelled(true);
		}
	}

}
