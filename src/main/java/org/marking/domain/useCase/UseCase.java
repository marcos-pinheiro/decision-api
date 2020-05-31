package org.marking.domain.useCase;

import org.marking.domain.useCase.command.Command;

public interface UseCase<T extends Command, R> {
	
	R execute(T command);
	
}
