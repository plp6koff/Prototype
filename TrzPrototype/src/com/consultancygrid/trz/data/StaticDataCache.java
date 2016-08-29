package com.consultancygrid.trz.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;

public class StaticDataCache {

	
	private static Map<UUID, TrzStaticData> cache = new HashMap<UUID, TrzStaticData>();
	
	private StaticDataCache() {

	}
	
	public static TrzStaticData getData(UUID id, EntityManager em ) {
	
		if (cache.containsKey(id)) {
			return cache.get(id);
		} else { 
			TrzStaticData data =  StaticDataLoader.load(id, em);
			cache.put(id, data);
			return data;
		}
	}
	
	public static TrzStaticData getData(UUID id) {
		
		if (cache.containsKey(id)) {
			return cache.get(id);
		} else { 
			TrzStaticData data =  StaticDataLoader.load(id);
			cache.put(id, data);
			return data;
		}
	}

	
	
}
