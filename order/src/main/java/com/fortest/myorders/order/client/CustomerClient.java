package com.fortest.myorders.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerClient {

    @GetMapping("/customers/{customerId}")
    Customer getCustomerById(@PathVariable("customerId") Long customerId);
}
