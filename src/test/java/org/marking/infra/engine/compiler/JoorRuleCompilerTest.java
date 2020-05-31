package org.marking.infra.engine.compiler;

import org.joor.ReflectException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.marking.domain.core.FactMap;
import org.marking.domain.core.ResultMap;
import org.marking.domain.core.Rules;

public class JoorRuleCompilerTest {
	
	static JoorRuleCompiler compiler;
	
	@BeforeAll
	static void beforeEach() {
		compiler = new JoorRuleCompiler();
	}
	
	
	@Test
	public void compile_WithRuleImplJavaCodeAndValidFacts_ShouldReturnRuleObjectWithValidRule() {
		ResultMap resultMap = new ResultMap();
		
		FactMap factMap = new FactMap("test-rule-domain");
		factMap.put("nickname", "mark");
		
		String code =
				"package org.marking.infra.engine.runtime;\n" + 
				
				"import org.marking.domain.core.Rules;\n" + 
				"import org.marking.domain.core.Fact;\n" + 
				"import org.marking.domain.core.Result;\n" + 
				 
				"public class TestRulesV1 implements Rules {\n" + 
				 
				"  @Override\n" + 
				"  public void run(Fact fact, Result result){\n" + 
				"    if(\"mark\".equals(fact.get(\"nickname\"))) {\n" + 
				"      result.put(\"name\", \"Marcos\");\n" + 
				"    }\n" + 
				"  }\n" + 
				"}\n";
		
		Rules rules = compiler.compile("org.marking.infra.engine.runtime.TestRulesV1", code);
		rules.run(factMap, resultMap);
		
		Assertions.assertEquals("Marcos", resultMap.values().get("name"));
		Assertions.assertEquals(1, resultMap.values().size());
	}
	
	@Test
	public void compile_WithRuleImplJavaCodeAndInvalidFacts_ShouldReturnRuleObjectWithInvalidRule() {
		ResultMap resultMap = new ResultMap();
		
		FactMap factMap = new FactMap("test-rule-domain");
		factMap.put("age", "27");
		
		String code =
				"package org.marking.infra.engine.runtime;\n" + 
				
				"import org.marking.domain.core.Rules;\n" + 
				"import org.marking.domain.core.Fact;\n" + 
				"import org.marking.domain.core.Result;\n" + 
				 
				"public class TestRulesV1 implements Rules {\n" + 
				 
				"  @Override\n" + 
				"  public void run(Fact fact, Result result){\n" + 
				"    if(\"mark\".equals(fact.get(\"nickname\"))) {\n" + 
				"      result.put(\"name\", \"Marcos\");\n" + 
				"    }\n" + 
				"  }\n" + 
				"}\n";
		
		Rules rules = compiler.compile("org.marking.infra.engine.runtime.TestRulesV1", code);
		rules.run(factMap, resultMap);
		
		Assertions.assertTrue(resultMap.values().isEmpty());
	}
	
	@Test
	public void compile_WithInvalidMethodInJavaCode_ShouldThrowExceptionByCompilationError() {		
		String code =
				"package org.marking.infra.engine.runtime;\n" + 
				
				"import org.marking.domain.core.Rules;\n" + 
				"import org.marking.domain.core.Fact;\n" + 
				"import org.marking.domain.core.Result;\n" + 
				 
				"public class TestRulesV1 implements Rules {\n" + 
				 
				"  @Override\n" + 
				"  public void method1(){\n" + 
				"    if(\"mark\".equals(fact.get(\"nickname\"))) {\n" + 
				"      result.put(\"name\", \"Marcos\");\n" + 
				"    }\n" + 
				"  }\n" + 
				"}\n";
		
		Assertions.assertThrows(ReflectException.class, () -> {
			compiler.compile("org.marking.infra.engine.runtime.TestRulesV1", code);	
		});
	}
}
