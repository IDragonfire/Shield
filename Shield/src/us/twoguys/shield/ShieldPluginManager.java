package us.twoguys.shield;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ShieldPluginManager implements ShieldAPI{
	
	Shield plugin;
	static ArrayList<String> plugins = new ArrayList<String>();
	
	public ShieldPluginManager(Shield instance){
		plugin = instance;
	}
	

	@Override
	public boolean isInRegion(Entity entity) {
		Object[] args = {entity};
		ArrayList<Boolean> outcomes = getOutcomes("isInRegion", args);
		
		for (boolean outcome: outcomes){
			plugin.log("Outcome: " + outcome);
			if (outcome == true){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isInRegion(Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBuild(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBuild(Player player, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canUse(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canUse(Player player, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canOpen(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canOpen(Player player, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public ArrayList<String> getInstantiatedPluginClassesArrayList(){
		return plugins;
	}
	
	public void addClassToInstantiatedPluginClassesArrayList(String className){
		//plugin.log("added " + className + " to pm class list");
		plugins.add("Protect_" + className);
	}
	
	public ArrayList<Boolean> getOutcomes(String methodName, Object[] args){
		ArrayList<Boolean> outcomes = new ArrayList<Boolean>();
		@SuppressWarnings("unchecked")
		ArrayList<String> temp = (ArrayList<String>) plugins.clone();
		
		plugin.log("Getting Outcomes");
		
		for (String className: temp){
			//plugin.log("Class name is " + className);
			Class<?> protect = null;
			try {
				protect = Class.forName("us.twoguys.shield.plugins." + className, true, this.getClass().getClassLoader());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			if (protect == null){
				//plugin.log("Class is null");
			}else{
				//plugin.log(protect.getName());
			}
			
			Object obj = null;
			try {
				obj = ((Class<?>)protect).getConstructor(Class.forName("us.twoguys.shield.Shield")).newInstance(plugin);
			} catch (Exception e){
				e.printStackTrace();
			}
			
			if (obj == null){
				//plugin.log("Obj is null");
			}else{
				//plugin.log("Obj successful");
			}
			
			Method[] methods = protect.getDeclaredMethods();
			
			//plugin.log("Looking for " + methodName);
			
			boolean outcome = false;
			boolean assigned = false;
			
			for (Method method: methods){
				
				if (assigned == true){break;}
				
				//plugin.log(method.getName());
				
				Class<?>[] methodArgsTypes = method.getParameterTypes();
				Class<?>[] argsTypes = new Class<?>[args.length];
				
				int counter = 0;
				
				for (Object arg: args){
					if (arg instanceof Entity){
						argsTypes[counter] = Entity.class;
						counter++;
					}else if (arg instanceof Location){
						argsTypes[counter] = Location.class;
						counter++;
					}
				}
				
				if (method.getName().equalsIgnoreCase(methodName)){
					
					try {
						if (methodArgsTypes.length == 0){
							outcome = (Boolean) method.invoke(obj);
							assigned = true;
						}else if (methodArgsTypes.length == 1){
							if (argsTypes[0] == methodArgsTypes[0]){
								outcome = (Boolean) method.invoke(obj, args[0]);
								assigned = true;
							}
						}else if (methodArgsTypes.length == 2){
							if (argsTypes[0] == methodArgsTypes[0]){
								outcome = (Boolean) method.invoke(obj, args[0], args[1]);
								assigned = true;
							}else if (argsTypes[0] == methodArgsTypes[1]){
								outcome = (Boolean) method.invoke(obj, args[1], args[0]);
								assigned = true;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if (assigned == true){
						//plugin.log("added outcome: " + outcome);
						outcomes.add(outcome);
					}
				}
			}
			
		}
		return outcomes;
	}

}
