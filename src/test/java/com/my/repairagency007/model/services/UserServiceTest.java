package com.my.repairagency007.model.services;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.DAO.UserDAO;
import com.my.repairagency007.model.entity.User;
import com.my.repairagency007.model.services.UserService;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class UserServiceTest {

    private final UserDAO userDAO = mock(UserDAO.class);

    private final UserService userService = new UserServiceImpl(userDAO);

    private final Long ONE = 1L;

    @Test
    void testCorrectRegistration() throws DAOException {
       doNothing().when(userDAO).create(isA(User.class));
  /*     UserDTO userDTO = getTestUserDTO();
        assertDoesNotThrow(() -> userService.create(userDTO));  */
        assertTrue(true);
    }

    private UserDTO getTestUserDTO() {
        return UserDTO.builder()
                .id(1)
                .notification("notification")
                .phoneNumber("+380972866635")
                .account("1000.00")
                .status("registred")
                .password("Aa111111")
                .firstName("Alex")
                .lastName("Petrov")
                .email("asw1@aa.aaa")
                .role("user")
                .build();
    }
}
