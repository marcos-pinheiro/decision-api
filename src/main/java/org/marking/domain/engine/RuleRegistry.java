package org.marking.domain.engine;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;

import org.marking.domain.core.Rules;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Singleton
public class RuleRegistry {
	
	private static final Map<String, Entry> PRIMARY_REGISTRY = new ConcurrentHashMap<>();
	private static final Map<String, Entry> TEMPORARY_REGISTRY = new ConcurrentHashMap<>();

	public final void override(String name, String version, Rules rules) {
		Entry registryEntry = TEMPORARY_REGISTRY.getOrDefault(name, new Entry(version, rules));
		PRIMARY_REGISTRY.put(name, registryEntry);
//		TEMPORARY_REGISTRY.remove(name);
	}
	
	public final void prepare(String name, String version, Rules rules) {
		TEMPORARY_REGISTRY.put(name, new Entry(version, rules));
	}
	
	public final String currentVersion(String name) {
		Entry registryEntry = PRIMARY_REGISTRY.get(name);
		if(registryEntry == null) {
			return null;
		}
		
		return registryEntry.getVersion();
	}
	
	public final Optional<Rules> get(String name) {
		return Optional.empty();
	}
	
	@AllArgsConstructor @Getter
	private static class Entry {
		private String version;
		private Rules content;
	}
}

