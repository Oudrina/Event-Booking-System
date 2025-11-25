package com.events.eventsbooking.auth;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthenticationToken{
    private  String  token;
}
