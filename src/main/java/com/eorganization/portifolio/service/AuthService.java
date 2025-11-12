package com.eorganization.portifolio.service;

import com.eorganization.portifolio.payload.*;

public interface AuthService {

    AuthResponse login(AuthRequest request);

    AuthResponse refresh(String refreshToken);

    void register(RegisterRequest request);

    void logout(String refreshToken);
}
