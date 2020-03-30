package net.lectusBUILD.thread;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

import net.lectusAPI.utils.StringUtils;

public class SignThread {

	public static void signReload() {

		for (World world : Bukkit.getWorlds()) {
			String name = world.getName();
			for (Chunk chunk : Bukkit.getWorld(name).getLoadedChunks()) {
				for (BlockState bs : chunk.getTileEntities()) {
					if (bs instanceof Sign) {
						Sign sign = (Sign) bs.getBlock().getState();
						sign.setLine(0, StringUtils.formatColor(sign.getLine(0)));
						sign.setLine(1, StringUtils.formatColor(sign.getLine(1)));
						sign.setLine(2, StringUtils.formatColor(sign.getLine(2)));
						sign.setLine(3, StringUtils.formatColor(sign.getLine(3)));
						sign.update();
					}
				}
			}
		}

	}

}
