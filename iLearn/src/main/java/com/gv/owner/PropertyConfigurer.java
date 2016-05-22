package com.gv.owner;

import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Reloadable;


@LoadPolicy(LoadType.MERGE)
@Sources({
	      "http://localhost:8080/iLearn/resources/airline.properties"
		  } )
public interface PropertyConfigurer extends Reloadable {
	
	int port();
	@DefaultValue("42345%")
    String hostname();
    
    @DefaultValue("42")
    int maxThreads();
    
    int timeout();
}
