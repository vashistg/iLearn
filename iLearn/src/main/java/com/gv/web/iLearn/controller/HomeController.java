package com.gv.web.iLearn.controller;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.gv.deadlock.DeadLock;
import com.gv.metrics.gauges.SuccessGauge;
import com.gv.metrics.healthcheck.ServiceHealthCheck;
import com.gv.metrics.listener.HealthMetricListener;
import com.gv.metrics.listener.MetricsServletContextListener;
import com.gv.owner.PropertyConfigurer;
import com.gv.web.iLearn.commands.hystrix.CircuitBreakerCommand;
import com.gv.web.iLearn.pojo.SearchRequest;
import com.gv.web.iLearn.pojo.SearchResponse;
import com.gv.web.iLearn.service.FlightService;
import com.gv.web.iLearn.service.impl.FlightServiceAPI;
import com.wordnik.swagger.annotations.ApiOperation;

import rx.Observable;
import rx.Subscriber;

@Controller
public class HomeController {

	@Autowired
	FlightService flightServiceProxy;

	PropertyConfigurer cfg = null;

	private final Meter requests = MetricsServletContextListener.METRIC_REGISTRY.meter("requests");
	private final Meter responses = MetricsServletContextListener.METRIC_REGISTRY.meter("responses");
	private final Histogram resultCounts = MetricsServletContextListener.METRIC_REGISTRY.histogram("resultSize");
	private final Timer timer = MetricsServletContextListener.METRIC_REGISTRY.timer("search-timer");
	private final Counter requestServed = MetricsServletContextListener.METRIC_REGISTRY.counter("requestServed");

	{
		MetricsServletContextListener.METRIC_REGISTRY.register("SuccessRate", new SuccessGauge(requests, responses));
		HealthMetricListener.HEALTH_CHECK_REGISTRY.register("apiHealthCheck", new ServiceHealthCheck());
		MetricsServletContextListener.METRIC_REGISTRY.register("gc", new GarbageCollectorMetricSet());
		// MetricsServletContextListener.METRIC_REGISTRY.register("memory", new
		// MemoryUsageGaugeSet());
		MetricsServletContextListener.METRIC_REGISTRY.register("threads", new ThreadStatesGaugeSet());
		// MetricsServletContextListener.METRIC_REGISTRY.register("buffers", new
		// BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));
		// MetricsServletContextListener.METRIC_REGISTRY.register("classLoaders",
		// new ClassLoadingGaugeSet());
		// MetricsServletContextListener.METRIC_REGISTRY.register("FD", new
		// FileDescriptorRatioGauge());
	}

	@RequestMapping(value = "/rest")
	public @ResponseBody SearchResponse restAPI(HttpServletResponse response) throws IOException {
		final Timer.Context context = timer.time();
		try {
			requests.mark();
			FlightService service = new FlightServiceAPI();
			SearchRequest req = new SearchRequest();
			req.setAsync(false);
			SearchResponse searchResponse = service.searchFlights(req);
			if (new Date().getTime()
					% 2 == 0/*
							 * searchResponse.getError() == null &&
							 * searchResponse.getFlights() != null &&
							 * searchResponse.getFlights().size() > 0
							 */) {
				responses.mark();
				resultCounts.update(searchResponse.getFlights().size());
			}
			return searchResponse;
		} finally {
			requestServed.inc();
			context.stop();
		}
	}

	@RequestMapping(value = "/aop")
	public @ResponseBody SearchResponse aopAPI(HttpServletResponse response) throws IOException {
		/**
		 * Need to understad this concept. Proxying in java CGLIB is used if the
		 * class does not implement any interface. It will create a sub class
		 * extending the base class. JDK Proxy is used if the class implements
		 * some interface
		 * 
		 **/
		/*
		 * ApplicationContext appContext = new ClassPathXmlApplicationContext(
		 * new String[] { "mvc-dispatcher-servlet.xml" }); FlightSearchRaw
		 * searchAPI =
		 * (FlightSearchRaw)appContext.getBean("flightServiceProxy");
		 */

		SearchRequest req = new SearchRequest();
		req.setAsync(false);
		return flightServiceProxy.searchFlights(req);
	}

	@RequestMapping(value = "/asyncTask")
	public @ResponseBody Callable<SearchResponse> callableAPI(HttpServletResponse response) throws IOException {
		final FlightService service = new FlightServiceAPI();
		Callable<SearchResponse> asyncTask = new Callable<SearchResponse>() {
			@Override
			public SearchResponse call() throws Exception {
				System.out.println("Callbale thread" + Thread.currentThread().getName());
				SearchRequest req = new SearchRequest();
				req.setAsync(true);
				SearchResponse searchResponse = service.searchFlights(req);
				return searchResponse;
			}
		};
		System.out.println("Async Method" + Thread.currentThread().getName());
		return asyncTask;
	}
		
	@RequestMapping(value = "/testDeadLock")
	public @ResponseBody String test(HttpServletResponse response) throws IOException {
		ModelAndView mv = new ModelAndView("home");
		new Thread(new Runnable() {
			public void run() {
				new DeadLock().run();
			}
		}).start();
		try {
			Thread.currentThread().sleep(100);
		} catch (Exception ex) {

		}
		new Thread(new Runnable() {
			public void run() {
				new DeadLock().dontRun();
			}
		}).start();

		return "Deadlock created.";
	}

	@RequestMapping(value = "/propertyFromURL")
	public @ResponseBody String callableAPI(String keyName, HttpServletResponse response) throws IOException {
	
		cfg = ConfigCache.getOrCreate(PropertyConfigurer.class);
		System.out.println(cfg);
		return "Server " + cfg.hostname() + ":" + cfg.port() + " will run " + cfg.maxThreads()
				+ " threads and with a timeout of " + cfg.timeout();
	}

	@RequestMapping(value = "/reloadProperty")
	@ApiOperation(value="reloads the properties of the code")
	public @ResponseBody String reloadProperties(HttpServletResponse response) throws IOException {
		PropertyConfigurer cfg = ConfigCache.getOrCreate(PropertyConfigurer.class);
		PropertyConfigurer cfg1 = ConfigFactory.create(PropertyConfigurer.class);
		cfg.reload();
		return "Server " + cfg.hostname() + ":" + cfg.port() + " will run " + cfg.maxThreads()
				+ " threads and with a timeout of " + cfg.timeout();
	}

	@RequestMapping(value = "/reportMeterics")
	public @ResponseBody String recordMetrics(HttpServletResponse response) throws IOException {
		ConsoleReporter reporter = ConsoleReporter.forRegistry(MetricsServletContextListener.METRIC_REGISTRY)
				.convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
		reporter.start(10, TimeUnit.SECONDS);
		return "";
	}
	
	@RequestMapping(value = "/forceClosed/{groupKey}/{key}/{forceOpen}")
	public @ResponseBody Boolean forceOpen(HttpServletResponse response,@PathVariable("groupKey")String  groupKey,@PathVariable("key")String  key,@PathVariable("forceOpen") String forceOpen ) throws IOException {
		return new CircuitBreakerCommand(groupKey, key, Boolean.parseBoolean(forceOpen)).execute();
	}
	
	
}
