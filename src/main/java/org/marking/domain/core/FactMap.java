package org.marking.domain.core;

import java.util.HashMap;
import java.util.Map;

public class FactMap implements Fact {

	private final String domain;
	private final Map<String, String> data;
	
	public FactMap(String domain) {
		this.domain = domain;
		this.data = new HashMap<>();
	}
	
	
	@Override
	public String getDomain() {
		return domain;
	}

	@Override
	public String get(String field) {
		return data.get(field);
	}
	
	public void put(String field, String value) {
		data.put(field, value);
	}
}
