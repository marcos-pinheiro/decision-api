package org.marking.infra.resources.grpc;

import java.util.Optional;

import javax.inject.Named;
import javax.inject.Singleton;

import org.marking.domain.useCase.UseCase;
import org.marking.domain.useCase.command.CreateDeploymentCommand;
import org.marking.infra.resources.grpc.CreateDeploymentResponse.Builder;

import io.grpc.stub.StreamObserver;

@Singleton
public class DeploymentEndpoint extends DeploymentGrpc.DeploymentImplBase {
	
	private final UseCase<CreateDeploymentCommand, Optional<String>> createDeployment;
	
	public DeploymentEndpoint(@Named("createDeploymentUseCase") UseCase<CreateDeploymentCommand, Optional<String>> createDeployment) {
		this.createDeployment = createDeployment;
	}


	@Override
	public void createDeployment(CreateDeploymentRequest request, StreamObserver<CreateDeploymentResponse> responseObserver) {
		CreateDeploymentCommand command = CreateDeploymentCommand.builder()
				.version(request.getVersion())
				.ruleGroupName(request.getRuleGroupName())
				.deploymentName(request.getDeploymentName())
				.strategy(request.getStrategy())
				.build();
		
		Builder builder = CreateDeploymentResponse.newBuilder()
				.setStatus("DONE");
		
		createDeployment.execute(command)
			.ifPresent(builder::setPreviousVersion);
		
		responseObserver.onNext(builder.build());
		responseObserver.onCompleted();
	}
}
