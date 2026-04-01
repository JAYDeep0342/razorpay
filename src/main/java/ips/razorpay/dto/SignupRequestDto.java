package ips.razorpay.dto;


import lombok.Data;

@Data
public class SignupRequestDto {
    private String username;
    private String password;
    private String name;
    private String email;
}
