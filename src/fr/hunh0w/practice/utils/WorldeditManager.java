package fr.hunh0w.practice.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.bukkit.World;

import com.boydti.fawe.object.schematic.Schematic;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.registry.WorldData;

import fr.hunh0w.practice.arenas.objects.Arena;
import fr.hunh0w.practice.managers.GlobalsManager;

public class WorldeditManager {
	
	public static void saveSchematic(Arena rg) {
		File f = new File(GlobalsManager.arenasfolder, rg.getName()+".schematic");
		saveSchem(f.getPath(), rg.getLocation1().getX(), rg.getLocation1().getY(), rg.getLocation1().getZ(), rg.getLocation2().getX(), rg.getLocation2().getY(), rg.getLocation2().getZ(), rg.getLocation1().getWorld());
	}
	
	public static void loadSchematic(Arena rg) {
		File f = new File(GlobalsManager.arenasfolder, rg.getName()+".schematic");
		loadArea(f, rg.getLocation1().getWorld());
	}
	
	private static void loadArea(File file, World w){

			try {
				BukkitWorld weWorld = new BukkitWorld(w);
				WorldData worldData = weWorld.getWorldData();
				Clipboard clipboard = ClipboardFormat.SCHEMATIC.getReader(new FileInputStream(file)).read(worldData);
				Schematic schem = new Schematic(clipboard);
				schem.paste(weWorld, clipboard.getOrigin(), true);
			}catch(Exception e) {
				e.printStackTrace();
			}

		
	}
	
	@SuppressWarnings("deprecation")
	private static void saveSchem(String filename, double x1, double y1, double z1, double x2, double y2, double z2, org.bukkit.World world) {
        File f = new File(filename);
        
		BukkitWorld weWorld = new BukkitWorld(world);
        Vector pos1 = new Vector(x1, y1, z1); //First corner of your cuboid
        Vector pos2 = new Vector(x2, y2, z2); //Second corner fo your cuboid
        CuboidRegion cReg = new CuboidRegion(weWorld, pos1, pos2);
        Schematic schem = new Schematic(cReg);
        try {
			schem.save(f, ClipboardFormat.SCHEMATIC);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
