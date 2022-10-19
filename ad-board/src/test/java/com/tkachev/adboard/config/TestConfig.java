package com.tkachev.adboard.config;

import com.tkachev.adboard.dto.mappers.*;
import com.tkachev.adboard.repositories.*;
import com.tkachev.adboard.security.JwtTokenProvider;
import com.tkachev.adboard.security.SecurityUserFactory;
import com.tkachev.adboard.services.*;
import com.tkachev.adboard.services.impl.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class TestConfig {

    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ChatRepository chatRepository;
    @MockBean
    private MessageRepository messageRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private AdRepository adRepository;
    @MockBean
    private ReviewRepository reviewRepository;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private SecurityUserFactory securityUserFactory;

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository, userMapper(), passwordEncoder);
    }

    @Bean
    public ChatService chatService() {
        return new ChatServiceImpl(chatRepository, userRepository, chatMapper());
    }

    @Bean
    public MessageService messageService() {
        return new MessageServiceImpl(messageRepository, chatRepository, userRepository, messageMapper());
    }

    @Bean
    public CategoryService categoryService() {
        return new CategoryServiceImpl(categoryRepository, categoryMapper());
    }

    @Bean
    public AdService adService() {
        return new AdServiceImpl(adRepository, userRepository, categoryRepository, adMapper());
    }

    @Bean
    public ReviewService reviewService() {
        return new ReviewServiceImpl(reviewRepository, userRepository, adRepository, reviewMapper());
    }

    @Bean
    public AuthenticationService authenticationService() {
        return new AuthenticationServiceImpl(authenticationManager, jwtTokenProvider, userRepository);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userRepository, securityUserFactory);
    }


    @Bean
    public UserMapper userMapper() {
        return new UserMapperImpl();
    }

    @Bean
    public MessageMapper messageMapper() {
        return new MessageMapperImpl();
    }

    @Bean
    public ChatMapper chatMapper() {
        return new ChatMapperImpl(messageMapper(), userMapper());
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return new CategoryMapperImpl();
    }

    @Bean
    public AdMapper adMapper() {
        return new AdMapperImpl(userMapper());
    }

    @Bean
    public ReviewMapper reviewMapper() {
        return new ReviewMapperImpl();
    }
}
