/*
 * Copyright 2012 Jordan Hobgood
 * 
 * This file is part of Shield.
 *
 * Shield is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Shield is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with Shield.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.malikk.shield.plugins;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.malikk.shield.*;
import com.malikk.shield.regions.ShieldRegion;

public class Protect_YourPlugin implements Listener, Protect {
	
	Shield shield;
	
	private final String name = "Plugin Name";
	private final String pack = "location.of.main.class";
	private static YourPlugin protect = null;
	
	public Protect_YourPlugin(Shield instance){
		this.shield = instance;
		
		PluginManager pm = shield.getServer().getPluginManager();
		pm.registerEvents(this, shield);
		
		//Load plugin if it was loaded before Shield
		if (protect == null) {
			Plugin p = shield.getServer().getPluginManager().getPlugin(name);
            
			if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
				protect = (YourPlugin) p;
				shield.pm.addClassToInstantiatedSet(this);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginEnable(PluginEnableEvent event) {
		if (protect == null) {
			Plugin p = shield.getServer().getPluginManager().getPlugin(name);

			if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
				protect = (YourPlugin) p;
				shield.pm.addClassToInstantiatedSet(this);
				shield.log(String.format("Hooked %s v" + getVersion(), name));
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginDisable(PluginDisableEvent event) {
		if (protect != null) {
			if (event.getPlugin().getDescription().getName().equals(name)) {
				protect = null;
				shield.log(String.format("%s unhooked.", name));
			}
		}
	}
	
	@Override
	public boolean isEnabled(){
		return (protect == null ? false : true);
	}
	
	@Override
	public String getPluginName(){
		return name;
	}
	
	@Override
	public String getVersion(){
		return protect.getDescription().getVersion();
	}
	
	/*
	 * If you can figure out how to make ShieldRegions, great, otherwise skip the first 3 methods
	 * 
	 * shield.rm.createShieldRegion(String regionName, Protect objectForThisClass, World world));
	 *
	 */
	@Override
	public HashSet<ShieldRegion> getRegions() {
		// TODO Return a HashSet of all the regions under your plugin
		return null;
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Entity entity) {
		// TODO Return all the regions that the entity is in
		return null;
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Location loc) {
		// TODO Return all the regions the location is in
		return null;
	}

	@Override
	public boolean isInRegion(Entity entity) {
		// TODO Return whether or not the entity is in any regions
		return false;
	}

	@Override
	public boolean isInRegion(Location loc) {
		// TODO Return whether or not the location is in any regions
		return false;
	}

	@Override
	public boolean canBuild(Player player) {
		// TODO Return whether or not the player can build at their current location
		return false;
	}
	
	@Override
	public boolean canBuild(Player player, Location loc) {
		// TODO Return whether or not the player can build at the specified location
		return false;
	}

	@Override
	public boolean canUse(Player player) {
		// TODO Return whether or not the player can use doors, buttons, levers, etc. at their current location
		return false;
	}

	@Override
	public boolean canUse(Player player, Location loc) {
		// TODO Return whether or not the player can use doors, buttons, levers, etc. at the specified location
		return false;
	}

	@Override
	public boolean canOpen(Player player) {
		// TODO Return whether or not the player can open chests, furnaces, and dispensers at their current location
		return false;
	}

	@Override
	public boolean canOpen(Player player, Location loc) {
		// TODO Return whether or not the player can open chests, furnaces, and dispensers at the specified location
		return false;
	}
	
	//Region info Getters
	@Override
	public Location getMaxLoc(ShieldRegion region) {
		// TODO Leave this blank, not sure if this will be implemented
		return null;
	}

	@Override
	public Location getMinLoc(ShieldRegion region) {
		// TODO Leave this blank, not sure if this will be implemented
		return null;
	}

	@Override
	public boolean contains(ShieldRegion region, Location loc) {
		// TODO Return whether or not the ShieldRegion contains the specified location
		return false;
	}

}