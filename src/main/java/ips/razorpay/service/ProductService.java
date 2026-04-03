package ips.razorpay.service;

import com.cloudinary.Cloudinary;
import ips.razorpay.dto.ProductResponseDto;
import ips.razorpay.entity.Product;
import ips.razorpay.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final Cloudinary cloudinary;

    public ProductResponseDto createProduct(
            String name,
            int price,
            int quantity,
            String sellerName,
            MultipartFile image
    ) {

        try {
            //  Upload image to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), Map.of());
            String imageUrl = uploadResult.get("secure_url").toString();

            // 🔥 Save Product
            Product product = Product.builder()
                    .name(name)
                    .price(price)
                    .quantity(quantity)
                    .sellerName(sellerName)
                    .imageUrl(imageUrl)
                    .createdAt(LocalDateTime.now())
                    .build();

            Product saved = productRepository.save(product);

            // 🔥 Return Response DTO
            return ProductResponseDto.builder()
                    .id(saved.getId())
                    .name(saved.getName())
                    .price(saved.getPrice())
                    .quantity(saved.getQuantity())
                    .imageUrl(saved.getImageUrl())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Product creation failed", e);
        }
    }

    public @Nullable List<ProductResponseDto> geTAllproducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> ProductResponseDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .imageUrl(product.getImageUrl())
                        .build()
                ).toList();
    }
}