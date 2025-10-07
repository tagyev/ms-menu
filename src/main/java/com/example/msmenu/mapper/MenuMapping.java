package com.example.msmenu.mapper;

import com.example.msmenu.dao.entity.MenuEntity;
import com.example.msmenu.dto.request.MenuRequest;
import com.example.msmenu.dto.response.MenuResponse;

import java.time.LocalDateTime;

import static com.example.msmenu.enums.MenuStatus.AVAILABLE;

public enum MenuMapping {
    MENU;
    public MenuEntity requestToEntity(MenuRequest request) {
        return MenuEntity.builder()
                .restoranId(request.getRestoranId())
                .productName(request.getProductName())
                .price(request.getPrice())
                .status(AVAILABLE)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public MenuResponse entityToResponse(MenuEntity entity) {
        return MenuResponse.builder()
                .id(entity.getId())
                .restoranId(entity.getRestoranId())
                .productName(entity.getProductName())
                .price(entity.getPrice())
                .status(entity.getStatus())
                .build();
    }
}
