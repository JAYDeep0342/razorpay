package ips.razorpay.service;

import com.razorpay.RazorpayClient;
import ips.razorpay.entity.Order;
import ips.razorpay.entity.Product;
import ips.razorpay.repository.OrderRepository;
import ips.razorpay.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RazorpayClient razorpayClient;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public Map<String, Object> createOrder(Long productId) {

        try {
            // 🔥 1. Product fetch
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            int amount = product.getPrice();

            // 🔥 2. Razorpay order create
            JSONObject options = new JSONObject();
            options.put("amount", amount * 100); // paise
            options.put("currency", "INR");
            options.put("receipt", "order_" + System.currentTimeMillis());

            com.razorpay.Order razorpayOrder = razorpayClient.orders.create(options);

            // 🔥 3. Save in DB
            Order order = Order.builder()
                    .razorpayOrderId(razorpayOrder.get("id"))
                    .amount(amount)
                    .status("CREATED")
                    .productId(productId)
                    .build();

            orderRepository.save(order);

            // 🔥 4. Response
            Map<String, Object> response = new HashMap<>();
            response.put("orderId", razorpayOrder.get("id"));
            response.put("amount", amount * 100);

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Order creation failed", e);
        }
    }
}