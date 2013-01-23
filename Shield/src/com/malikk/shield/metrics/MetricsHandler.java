package com.malikk.shield.metrics;

import java.io.IOException;

import com.malikk.shield.Shield;
import com.malikk.shield.metrics.Metrics.Plotter;
import com.malikk.shield.plugins.Protect;

public class MetricsHandler {

	Shield plugin;
	Metrics metrics = null;
	
	public MetricsHandler(Shield instance){
		plugin = instance;
		
		try {
			metrics = new Metrics(plugin);
			startMetrics();
		} catch (IOException e) {
			plugin.logWarning("Failed to start plugin Metrics.");
		}
	}
	
	private void startMetrics(){
		
		setGraphPlotters();
		
		metrics.start();
	}
	
	private void setGraphPlotters(){
	        // TODO: dynmaicly
	        final String[] plugins = new String[]{"WorldGuard", "Residence", "Regios", "PreciousStones", "AntiShare", "Factions"};
	        
	        for (int i = 0; i < plugins.length; i++) {
	            metrics.addCustomData(new SimpleMetricPlotter(plugin.getProtectPlugin(plugins[i])));
	        }
	}
	
	public class SimpleMetricPlotter extends Plotter {
	    private int usage;
	    
            public SimpleMetricPlotter(Protect plugin) {
                this.usage = (plugin != null ? 1 : 0);
            }
            
            @Override
            public int getValue() {
                return usage;
            }
	    
	}
}
