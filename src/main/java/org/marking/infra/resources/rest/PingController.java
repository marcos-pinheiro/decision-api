package org.marking.infra.resources.rest;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/ping")
public class PingController {
	
	@Get("/")
    public String pong() {
        return "pong"; 
    }
}
