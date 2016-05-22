package com.gv.async.qbit;

import io.advantageous.qbit.admin.ManagedServiceBuilder;
import io.advantageous.qbit.annotation.RequestMapping;
import io.advantageous.qbit.annotation.http.GET;
import io.advantageous.qbit.annotation.http.PUT;
import static io.advantageous.qbit.admin.ManagedServiceBuilder.managedServiceBuilder;

/**
 * curl  -H "Content-Type: application/json"  -X PUT http://localhost:8080/trade -d '{"name":"ibm", "amount":1}'
 * curl  http://localhost:8080/count
 */
@RequestMapping("/")
public class TradeService {

    private long count;

    @PUT("/trade")
    public boolean trade(final Trade trade) {
        trade.getName().hashCode();
        trade.getAmount();
        count++;
        return true;
    }

    @GET("/count")
    public long count() {
        return count;
    }

    public static void main(final String... args) {

        final ManagedServiceBuilder managedServiceBuilder = managedServiceBuilder();

        managedServiceBuilder
                .addEndpointService(new TradeService())
                .setRootURI("/");

        managedServiceBuilder.startApplication();
    }
}
