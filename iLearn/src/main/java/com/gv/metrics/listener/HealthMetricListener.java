package com.gv.metrics.listener;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;

public class HealthMetricListener extends HealthCheckServlet.ContextListener{

	public static final HealthCheckRegistry HEALTH_CHECK_REGISTRY = new HealthCheckRegistry();
	
	@Override
	protected HealthCheckRegistry getHealthCheckRegistry() {
		return HEALTH_CHECK_REGISTRY;
	}

}
