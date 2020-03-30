package net.lectusBUILD.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;

import net.lectusAPI.MainLectusApi;
import net.lectusAPI.cache.PlayerCache;
import net.lectusAPI.grade.Rank;
import net.lectusAPI.utils.BukkitPermissionsUtils;
import net.lectusAPI.utils.TeamUtils;
import net.lectusAPI.utils.VanishUtils;
import net.lectusBUILD.MainLectusBuild;

public class JoinEvent implements Listener {

	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage(null);
		VanishUtils.getInstance().checkVanish(p);
		VanishUtils.getInstance().disableVanish(p);
		if (MainLectusApi.getInstance().getSql().hasModMode(p)) {
			MainLectusBuild.getInstance().mod.add(p);
		}
		if (!MainLectusBuild.getInstance().mod.contains(p)) {
			for (Player playerToHide : MainLectusBuild.getInstance().mod) {
				VanishUtils.getInstance().enableVanish(playerToHide);
			}
		}
		if (MainLectusApi.getInstance().getSql().hasModMode(p)) {
			e.setJoinMessage(null);
			MainLectusBuild.getInstance().mod.add(p);
			VanishUtils.getInstance().enableVanish(p);
			p.getInventory().clear();
		} else {
			Bukkit.getScheduler().runTaskLater(MainLectusBuild.getInstance(), new Runnable() {
				@Override
				public void run() {
					if (!Rank.BUILDEUR.hasPermission(p)) {
						p.kickPlayer(
								"KickOnConnection (you don't have the permission to join this server) : Tu n'as pas la permission de rejoindre ce serveur");
					} else {
						Bukkit.broadcastMessage(
								ChatColor.GREEN + "+ " + PlayerCache.getCacheByPlayer(p).getRank().getDisplayName()
										+ " " + p.getDisplayName() + ChatColor.GRAY + " a rejoint le build !");
						TeamUtils.getInstance().addPlayerToTeam(p, PlayerCache.getCacheByPlayer(p).getRank().getName());
					}
				}
			}, 1 * 5);
		}

		Rank playerRank = PlayerCache.getCacheByPlayer(p).getRank();
		if (playerRank == Rank.ADMIN || playerRank == Rank.BUILDEUR || playerRank == Rank.OWNER) {
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "minecraft.command.difficulty");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "minecraft.command.effect");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "minecraft.command.gamerule");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "minecraft.command.give");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "minecraft.command.setblock");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "minecraft.command.fill");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "minecraft.command.setworldspawn");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "minecraft.command.spawnpoint");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "minecraft.command.time");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "minecraft.command.weather");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "minecraft.command.xp");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "worldedit.*.");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "worldedit.*");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "multiverse.*.");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "multiverse.*");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "voxelsniper.sniper");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "voxelsniper.ignorelimitations");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "voxelsniper.goto");
			BukkitPermissionsUtils.addPermission(p.getUniqueId(), "voxelsniper.brush.*");
		}
		p.setGameMode(GameMode.CREATIVE);
		p.teleport(Bukkit.getWorld("world").getSpawnLocation());
	}

	@EventHandler
	public void quit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		for (PermissionAttachmentInfo attachmentInfo : p.getEffectivePermissions()) {
			try {
				attachmentInfo.getAttachment().unsetPermission(attachmentInfo.getPermission());
			} catch (Exception ex) {
				p.sendMessage("FATAL ERROR: Could not remove permission: " + attachmentInfo.getPermission());
			}
		}
		// SCOREBOARD and team
		TeamUtils.getInstance().removePlayerOfTeam(p, PlayerCache.getCacheByPlayer(p).getRank().getName());
		// SCOREBOARD and team
		if (MainLectusBuild.getInstance().mod.contains(p)) {
			e.setQuitMessage(null);
			MainLectusBuild.getInstance().mod.remove(p);
			VanishUtils.getInstance().disableVanish(p);
		}
		e.setQuitMessage(null);
	}

}
