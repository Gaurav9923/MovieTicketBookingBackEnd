package com.gaurav.movietkt.model.authModel;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
   public String token;
   public Integer userId;
}
