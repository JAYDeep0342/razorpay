package ips.razorpay.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDto {

    private Long id;
    private String name;
    private int price;
    private int quantity;
    private String imageUrl;
}
