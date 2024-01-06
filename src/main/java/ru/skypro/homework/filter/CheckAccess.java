package ru.skypro.homework.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public abstract class CheckAccess {
    private final UserRepository userRepository;
    public boolean isAdminOrOwnerComment(Principal principal) {
        boolean isTrue;
        UserEntity userEntity = userRepository.findByUsername(principal.getName());
        if (!userRepository)

        return isTrue;

    }
}
