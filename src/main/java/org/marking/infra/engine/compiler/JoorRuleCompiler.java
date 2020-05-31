package org.marking.infra.engine.compiler;

import javax.inject.Named;

import org.joor.Reflect;
import org.marking.domain.core.Rules;
import org.marking.domain.engine.RuleCompiler;

@Named
public class JoorRuleCompiler implements RuleCompiler<String> {
	
	@Override
	public Rules compile(String name, String content) {
		return Reflect.compile(name, content)
				.create()
				.get();
	}
}
