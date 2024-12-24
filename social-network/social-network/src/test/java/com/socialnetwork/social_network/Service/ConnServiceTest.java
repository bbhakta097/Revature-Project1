package com.socialnetwork.social_network.Service;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.socialnetwork.social_network.Model.Connection;
import com.socialnetwork.social_network.Model.User;
import com.socialnetwork.social_network.Repository.ConnRepo;
import com.socialnetwork.social_network.Repository.UserRepository;

class ConnServiceTest {

    @Mock
    private ConnRepo connectionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ConnService connService;

    private User mockUser;
    private User targetUser;
    private Connection mockConnection;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testUser");

        targetUser = new User();
        targetUser.setId(2L);
        targetUser.setUsername("targetUser");

        mockConnection = new Connection();
        mockConnection.setUserId(1L);
        mockConnection.setConnectedUserId(2L);
    }

    @Test
    void followUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userRepository.findById(2L)).thenReturn(Optional.of(targetUser));
        when(connectionRepository.findByUserIdAndConnectedUserId(1L, 2L)).thenReturn(Optional.empty());

        String result = connService.followUser(1L, 2L);

        assertEquals("You are now following the user.", result);
        verify(connectionRepository, times(1)).save(any(Connection.class));
    }

    @Test
    void getFollowers_Success() {
        when(connectionRepository.findAllByConnectedUserId(2L)).thenReturn(Arrays.asList(mockConnection));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        Long result = connService.getFollowers(2L);

        assertEquals(1, result);
        assertEquals("testUser", result);
    }
}
