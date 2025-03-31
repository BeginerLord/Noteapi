package com.appscol.user.service.implementation;

import com.appscol.user.factory.UserFactory;
import com.appscol.user.persistence.repositories.UserRepository;
import com.appscol.user.service.interfaces.IUserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements IUserServices {

    private final UserRepository userRepository;
    private final UserFactory userFactory;


}
