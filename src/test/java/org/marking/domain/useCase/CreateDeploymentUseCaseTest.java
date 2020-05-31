package org.marking.domain.useCase;

import static org.mockito.Mockito.doReturn;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.marking.domain.artifacts.ArtifactRepository;
import org.marking.domain.core.Rules;
import org.marking.domain.deployment.Deployment;
import org.marking.domain.deployment.DeploymentData;
import org.marking.domain.engine.RuleCompiler;
import org.marking.domain.useCase.command.CreateDeploymentCommand;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;


public class CreateDeploymentUseCaseTest {
	
	@InjectMocks @Spy
	CreateDeploymentUseCase useCase;
	
	@Mock ArtifactRepository artifactRepository;
	@Mock RuleCompiler<String> compiler;
	@Mock Deployment deployment;
	
	@BeforeEach
	void init() {
	    MockitoAnnotations.initMocks(this);
	}

	@Test
	public void execute_WhenPassedFirstDeployment_CompileAndReturnEmpty() {
		
		CreateDeploymentCommand command = CreateDeploymentCommand.builder()
				.deploymentName("my-first-deploy")
				.ruleGroupName("org.marking.infra.engine.runtime.TestRulesV1")
				.version("V1")
				.build();
		
		doReturn(javaCodeRule1())
			.when(artifactRepository).get(command.getRuleGroupName());
		
		doReturn(new DeploymentData(command.getDeploymentName(), command.getVersion()))
			.when(useCase).getDeploymentData(command);

		doReturn(Optional.empty())
			.when(deployment).deploy(Mockito.any(DeploymentData.class), Mockito.any(Rules.class));
		
		Optional<String> result = useCase.execute(command);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void execute_WhenPassedSecondDeployment_CompileAndReturnOldVersion() {
		
		CreateDeploymentCommand command = CreateDeploymentCommand.builder()
				.deploymentName("my-first-deploy")
				.ruleGroupName("org.marking.infra.engine.runtime.TestRulesV1")
				.version("V1")
				.build();
		
		doReturn(javaCodeRule1())
			.when(artifactRepository).get(command.getRuleGroupName());
		
		doReturn(new DeploymentData(command.getDeploymentName(), command.getVersion()))
			.when(useCase).getDeploymentData(command);

		doReturn(Optional.of(new DeploymentData("org.marking.infra.engine.runtime.TestRulesV0", "V0")))
			.when(deployment).deploy(Mockito.any(), Mockito.any());
		
		Optional<String> result = useCase.execute(command);
		
		Assertions.assertTrue(result.isPresent());
		Assertions.assertEquals("V0", result.get());
	}

	static String javaCodeRule1() {
		return 
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
	}
	
}
