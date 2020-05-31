package org.marking.infra.repositories.aws;

import javax.inject.Singleton;

import org.marking.domain.artifacts.ArtifactRepository;

@Singleton
public class S3ArtifactRepository implements ArtifactRepository {

	@Override
	public Object get(String artifact) {
		String content =
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
		
		return content;
	}
}
