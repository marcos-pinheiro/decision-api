package org.marking.domain.deployment;

import java.util.Optional;

import org.marking.domain.core.Rules;

public interface Deployment {
	
	Optional<DeploymentData> deploy(DeploymentData deploymentData, Rules rules);
	
	DeploymentStrategy getStrategy();
	
}
