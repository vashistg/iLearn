package com.gv.metrics.gauges;

import com.codahale.metrics.Meter;
import com.codahale.metrics.RatioGauge;

public class SuccessGauge extends RatioGauge {

	private final Meter requests;
    private final Meter responses;
	
    public SuccessGauge(Meter hits, Meter calls) {
        this.requests = hits;
        this.responses = calls;
    }
    
	@Override
	protected Ratio getRatio() {
	        return Ratio.of(responses.getCount(),requests.getCount());
	}
}
