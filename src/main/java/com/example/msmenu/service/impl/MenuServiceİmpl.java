package com.example.msmenu.service.impl;

import com.example.msmenu.dao.entity.MenuEntity;
import com.example.msmenu.dao.repository.MenuRepository;
import com.example.msmenu.dto.request.MenuRequest;
import com.example.msmenu.dto.request.OrderRequest;
import com.example.msmenu.dto.request.PaymentRequest;
import com.example.msmenu.dto.response.MenuResponse;
import com.example.msmenu.dto.response.PaymentResponse;
import com.example.msmenu.exception.MenuNotFoundException;
import com.example.msmenu.feignClient.PaymentClient;
import com.example.msmenu.feignClient.RestoranClient;
import com.example.msmenu.mapper.MenuMapping;
import com.example.msmenu.service.abstraction.MenuService;
import com.example.msmenu.util.CacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.math.BigDecimal.ZERO;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MenuServiceİmpl implements MenuService {
    MenuRepository repository;
    CacheUtil util;
    PaymentClient paymentClient;
    RestoranClient restoranClient;

    @Override
    public MenuResponse save(MenuRequest request) {
        restoranClient.findById(request.getRestoranId());
        MenuEntity entity = MenuMapping.MENU.requestToEntity(request);
        repository.save(entity);
        util.set(getKey(entity.getId()), entity, 10, TimeUnit.MINUTES);
        System.out.println("🟢 DB-dən oxundu və Redis-ə yazıldı!");
        return MenuMapping.MENU.entityToResponse(entity);
    }

    @Override
    public MenuResponse findById(Long id) {
        MenuEntity entitycache = util.get(getKey(id), MenuEntity.class);
        if (entitycache != null) {
            System.out.println("🔴 Redis-dən oxundu!");
            return MenuMapping.MENU.entityToResponse(entitycache);
        }
        MenuEntity entity = fetchMenuIfExist(id);
        util.set(getKey(entity.getId()), entity, 10, TimeUnit.MINUTES);
        System.out.println("🟢 DB-dən oxundu və Redis-ə yazıldı!");
        return MenuMapping.MENU.entityToResponse(entity);
    }

    @Override
    public PaymentResponse orderFood(OrderRequest request) {
        List<MenuEntity> menus = repository.findAllById(request.getMenuIds());
        if (menus.isEmpty()) {
            throw new MenuNotFoundException("Menu id not found");
        }
        BigDecimal totalPrice = ZERO;
        for (MenuEntity menu : menus) {
            totalPrice = totalPrice.add(menu.getPrice());
        }
        PaymentRequest pay = PaymentRequest.builder()
                .userId(request.getUserId())
                .amount(totalPrice)
                .build();
        PaymentResponse paymentResponse = paymentClient.makePayment(pay);
        return PaymentResponse.builder()
                .id(paymentResponse.getId())
                .userId(pay.getUserId())
                .amount(pay.getAmount())
                .build();
    }

    private MenuEntity fetchMenuIfExist(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new MenuNotFoundException("Menu not found: " + id));
    }

    private String getKey(Long id) {
        return "Menu:" + id;
    }
}
