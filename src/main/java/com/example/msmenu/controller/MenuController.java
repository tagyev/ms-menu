package com.example.msmenu.controller;

import com.example.msmenu.dto.request.MenuRequest;
import com.example.msmenu.dto.request.OrderRequest;
import com.example.msmenu.dto.response.MenuResponse;
import com.example.msmenu.dto.response.PaymentResponse;
import com.example.msmenu.service.abstraction.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MenuController {
    MenuService service;

    @PostMapping
    public MenuResponse save(@RequestBody MenuRequest request) {
       return service.save(request);
    }

    @GetMapping("/{id}")
    public MenuResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/order")
    public PaymentResponse orderFood(@RequestBody OrderRequest request) {
        return service.orderFood(request);
    }
}
