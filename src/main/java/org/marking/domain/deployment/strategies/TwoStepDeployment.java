package org.marking.domain.deployment.strategies;

import java.util.Optional;

import org.marking.domain.core.Rules;
import org.marking.domain.deployment.Deployment;
import org.marking.domain.deployment.DeploymentData;
import org.marking.domain.deployment.DeploymentStrategy;
import org.marking.domain.engine.RuleRegistry;

public class TwoStepDeployment implements Deployment {
	
	private final RuleRegistry registry;
	
	public TwoStepDeployment(RuleRegistry registry) {
		this.registry = registry;
	}
	

	@Override
	public Optional<DeploymentData> deploy(DeploymentData deploymentData, Rules rules) {
		registry.prepare(deploymentData.getName(), deploymentData.getVersion(), rules);
		return Optional.empty();
	}

	@Override
	public DeploymentStrategy getStrategy() {
		return DeploymentStrategy.TWO_STEP;
	}
}
