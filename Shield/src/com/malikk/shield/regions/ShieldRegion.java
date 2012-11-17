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

package com.malikk.shield.regions;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.malikk.shield.Shield;
import com.malikk.shield.groups.ShieldGroup;
import com.malikk.shield.plugins.Protect;

/**
 * A ShieldRegion is an object which represents a region in any given plugin. It stores the regions name, the plugin it belongs to, the world, and the Protect object.
 * @author Malikk
 *
 * @see {@link #getName()}
 * @see {@link #getPluginName()}
 * @see {@link #getWorld()}
 */
public class ShieldRegion {

	private Shield plugin;
	private String name;
	private World world;
	private Protect protect;

	public ShieldRegion (Shield instance, String name, Protect protect, World world){
		plugin = instance;

		this.name = name;
		this.protect = protect;
		this.world = world;
	}

	/**
	 * Gets the name of the region.
	 * 
	 * @return String - name
	 */
	public String getName(){
		return name;
	}

	/**
	 * Gets the name of the plugin the region belongs to.
	 * 
	 * @return String - plugin name
	 */
	public String getPluginName(){
		return protect.getPluginName();
	}

	protected Protect getProtectObject(){
		return protect;
	}

	/**
	 * Gets the World the region is in.
	 * 
	 * @return World - world
	 */
	public World getWorld(){
		return world;
	}

	/**
	 * Checks
	 * @param loc
	 * @return
	 */
	public boolean contains(Location loc){
		return plugin.rm.containsLoc(this, loc);
	}

	public boolean isOwner(Player player){
		ShieldGroup owners = protect.getOwners(this);
		return (owners.contains(player.getName()) ? true : false);
	}

	public ShieldGroup getOwners(){
		return protect.getOwners(this);
	}

	/*
	 * TODO Not sure if I will continue working on these methods, as not all regions will be cuboid
	 * 
	public Location getMaxLoc(){
		return plugin.rm.getMaxLoc(this);
	}

	public Location getMinLoc(){
		return plugin.rm.getMinLoc(this);
	}
	 */
}
