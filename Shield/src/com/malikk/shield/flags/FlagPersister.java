package com.malikk.shield.flags;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.malikk.shield.Shield;


public class FlagPersister {

	Shield plugin;
	
	public FlagPersister(Shield instance){
		plugin = instance;
	}
	
	public void save(){
		//create a new File
		File saveFile = new File(plugin.getDataFolder() + File.separator + "Flags.dat");

		if (!saveFile.exists()){
			try {
				plugin.getDataFolder().mkdir();
				saveFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		try{
			FileOutputStream fos = new FileOutputStream(plugin.getDataFolder() + File.separator + "Flags.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			//Write file here
			try{
				oos.writeInt(plugin.fm.flags.size());
			}catch(Exception e){
				plugin.log("No Flags to save!");
			}
			
			for (Flag f: plugin.fm.flags){
				oos.writeObject(f);
			}
				
			oos.close();
			
			plugin.log("Flags Saved");
			
		}catch(Exception e){
			e.printStackTrace();
			plugin.log("Failed to save Flags");
		}
		
				
	}
	
	public void load(){
		
		File saveFile = new File(plugin.getDataFolder() + File.separator + "Flags.dat");
		
		if(saveFile.exists()){
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			
			try{
				fis = new FileInputStream(saveFile);
				ois = new ObjectInputStream(fis);
				
				Integer recordCount = ois.readInt();
				int x = 0;
				
				for(int i = 0; i < recordCount; i ++){
					Flag f = (Flag) ois.readObject();
					plugin.fm.flags.add(f);
					
					x = i;
				}
				
				plugin.log(x + " Flags loaded");
					
			}catch(FileNotFoundException e){
				plugin.log("Could not locate data file... ");
				e.printStackTrace();
			}catch(IOException e){
				plugin.log("IOException while trying to read data file");
			}catch(ClassNotFoundException e){
				plugin.log("Could not find class to load");
			}finally{
				try{
					ois.close();
				}catch(IOException e){
					plugin.log("Error while trying to close input stream");
				}
			}
		}
	}
}