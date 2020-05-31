package org.marking.infra.engine;

import org.marking.domain.core.Fact;
import org.marking.domain.core.Result;
import org.marking.domain.engine.RuleEngine;
import org.marking.domain.engine.RuleRegistry;

public class RuleMapEngine implements RuleEngine {

	@Override
	public void fire(RuleRegistry registry, Fact fact, Result result) {
		registry.get(fact.getDomain())
			.ifPresent(rules -> rules.run(fact, result));
	}
}
