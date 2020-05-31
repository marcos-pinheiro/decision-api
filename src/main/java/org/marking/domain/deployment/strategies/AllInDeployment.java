package org.marking.domain.deployment.strategies;

import java.util.Optional;

import javax.inject.Named;
import javax.inject.Singleton;

import org.marking.domain.core.Rules;
import org.marking.domain.deployment.Deployment;
import org.marking.domain.deployment.DeploymentData;
import org.marking.domain.deployment.DeploymentStrategy;
import org.marking.domain.engine.RuleRegistry;

@Singleton @Named("allInDeployment")
public class AllInDeployment implements Deployment {
	
	private final RuleRegistry registry;
	
	public AllInDeployment(RuleRegistry registry) {
		this.registry = registry;
	}

	
	@Override
	public Optional<DeploymentData> deploy(DeploymentData deploymentData, Rules rules) {
		String currentVersion = registry.currentVersion(deploymentData.getName());
		registry.override(deploymentData.getName(), deploymentData.getVersion(), rules);
		
		if(currentVersion == null)
			return Optional.empty();
		
		return Optional.of(new DeploymentData(deploymentData.getName(), currentVersion));
	}

	@Override
	public DeploymentStrategy getStrategy() {
		return DeploymentStrategy.ALL_IN;
	}
}
