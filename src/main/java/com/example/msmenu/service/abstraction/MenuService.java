package com.example.msmenu.service.abstraction;

import com.example.msmenu.dto.request.MenuRequest;
import com.example.msmenu.dto.request.OrderRequest;
import com.example.msmenu.dto.response.MenuResponse;
import com.example.msmenu.dto.response.PaymentResponse;

public interface MenuService {
    MenuResponse save(MenuRequest request);

    MenuResponse findById(Long id);

     PaymentResponse orderFood(OrderRequest request);
}
