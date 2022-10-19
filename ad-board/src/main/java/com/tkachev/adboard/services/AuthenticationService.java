package com.tkachev.adboard.services;

import com.tkachev.adboard.dto.models.auth.AuthenticationRequest;
import com.tkachev.adboard.dto.models.auth.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest authData);
}
