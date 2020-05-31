package org.marking.domain.engine;

import org.marking.domain.core.Rules;

public interface RuleCompiler<T> {
	
	Rules compile(String name, T content);
	
}
