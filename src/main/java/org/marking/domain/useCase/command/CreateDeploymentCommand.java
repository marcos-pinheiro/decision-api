package org.marking.domain.useCase.command;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class CreateDeploymentCommand implements Command {

	private String version;
	private String ruleGroupName;
	private String deploymentName;
	private String strategy;

}
