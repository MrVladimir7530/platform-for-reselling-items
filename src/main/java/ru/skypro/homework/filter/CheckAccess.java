package ru.skypro.homework.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.security.Principal;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckAccess {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    public boolean isAdminOrOwnerComment(Integer commentId, Authentication authentication) {
        log.info("Was invoked method for verify of access");
        UserEntity userEntity = userRepository.findByUsername(authentication.getName());
        CommentEntity commentEntity = commentRepository.findById(commentId).get();
        return userEntity.getRole().name().equals("ADMIN")
                || userEntity.getId() == commentEntity.getUser().getId();

    }
}
