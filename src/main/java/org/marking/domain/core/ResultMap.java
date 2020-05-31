package org.marking.domain.core;

import java.util.HashMap;
import java.util.Map;

public class ResultMap implements Result {

	private final Map<String, String> internal = new HashMap<>();
	
	@Override
	public Map<String, String> values() {
		return internal;
	}

	@Override
	public void put(String key, String value) {
		internal.put(key, value);
	}
}
