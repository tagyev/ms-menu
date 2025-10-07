package com.example.msmenu.dto.response;

import com.example.msmenu.enums.MenuStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class MenuResponse {
    Long id;
    Long restoranId;
    String productName;
    BigDecimal price;
    MenuStatus status;
}
