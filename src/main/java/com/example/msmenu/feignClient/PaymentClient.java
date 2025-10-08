package com.example.msmenu.feignClient;

import com.example.msmenu.dto.request.PaymentRequest;
import com.example.msmenu.dto.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "ms.payment",
        url = "${ms.payment.url}"
)
public interface PaymentClient {
    @PostMapping
    PaymentResponse makePayment(@RequestBody PaymentRequest request);
}
