package com.example.msmenu.feignClient;

import com.example.msmenu.dto.response.RestoranResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "ms.restoran",
        url = "${ms.restoran.url}"
)
public interface RestoranClient {
    @GetMapping("/{id}")
    RestoranResponse findById(@PathVariable Long id);
}
