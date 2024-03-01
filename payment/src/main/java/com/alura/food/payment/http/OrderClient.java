package com.alura.food.payment.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("order-ms")
public interface OrderClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/orders/{id}/paid")
    void updatesPayment(@PathVariable Long id);

}
