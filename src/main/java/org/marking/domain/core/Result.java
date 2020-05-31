package org.marking.domain.core;

import java.util.Map;

public interface Result {
	
	Map<String, String> values();
	
	void put(String key, String value);
	
}
