package org.marking.domain.useCase.command;

import java.time.Instant;

public interface Command {
	
	default Instant createdAt() {
		return Instant.now();
	}
}
