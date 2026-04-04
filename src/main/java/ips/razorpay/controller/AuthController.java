package ips.razorpay.controller;

import ips.razorpay.dto.*;
import ips.razorpay.service.AuthService;
import ips.razorpay.service.OrderService;
import ips.razorpay.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")

@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ProductService productService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto dto) {
        return ResponseEntity.ok(authService.signup(dto));
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllproducts(){
        return ResponseEntity.ok(productService.geTAllproducts());
    }

}