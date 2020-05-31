package org.marking.domain.useCase;

import java.util.Optional;

import javax.inject.Named;
import javax.inject.Singleton;

import org.marking.domain.artifacts.ArtifactRepository;
import org.marking.domain.core.Rules;
import org.marking.domain.deployment.Deployment;
import org.marking.domain.deployment.DeploymentData;
import org.marking.domain.engine.RuleCompiler;
import org.marking.domain.useCase.command.CreateDeploymentCommand;

@Singleton @Named("createDeploymentUseCase")
public class CreateDeploymentUseCase implements UseCase<CreateDeploymentCommand, Optional<String>> {
	
	private final ArtifactRepository artefactRepository;
	private final RuleCompiler<String> compiler;
	private final Deployment deployment;

	public CreateDeploymentUseCase(ArtifactRepository artefactRepository, RuleCompiler<String> compiler, Deployment deployment) {
		this.artefactRepository = artefactRepository;
		this.compiler = compiler;
		this.deployment = deployment;
	}


	@Override
	public Optional<String> execute(CreateDeploymentCommand command) {
		Object ruleFile = artefactRepository.get(command.getRuleGroupName());
		Rules rules = compiler.compile(command.getRuleGroupName(), ruleFile.toString());

		return deployment.deploy(getDeploymentData(command), rules)
				.map(dep -> dep.getVersion());
	}

	DeploymentData getDeploymentData(CreateDeploymentCommand command) {
		return new DeploymentData(command.getDeploymentName(), command.getVersion());
	}
}