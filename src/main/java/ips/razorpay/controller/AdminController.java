package ips.razorpay.controller;


import ips.razorpay.dto.AdminRequestDto;
import ips.razorpay.dto.AdminResponseDto;
import ips.razorpay.repository.UserRepository;
import ips.razorpay.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class AdminController {
    private  final UserRepository userRepository;
    private  final AuthService authService ;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/createAdmin")
    public ResponseEntity<AdminResponseDto> CreateAdmin(@RequestBody AdminRequestDto dto ){

        return ResponseEntity.ok(authService.CreateAdmin(dto));
    }



}