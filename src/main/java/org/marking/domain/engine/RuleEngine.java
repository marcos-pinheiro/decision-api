package org.marking.domain.engine;

import org.marking.domain.core.Fact;
import org.marking.domain.core.Result;

public interface RuleEngine {

	void fire(RuleRegistry registry, Fact fact, Result result);

}

