package ru.skypro.homework.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public abstract class CheckAccess {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    public boolean isAdminOrOwnerComment(Integer commentId, Authentication authentication) {
        UserEntity userEntity = userRepository.findByUsername(authentication.getName());
        CommentEntity commentEntity = commentRepository.findById(commentId).get();
        if (userEntity.getRole().equals("ADMIN") || userEntity.getId() == commentEntity.getUser().getId()) {
            return true;
        }

        return false;

    }
}
