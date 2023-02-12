package com.my.repairagency007.model.services;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.DAO.UserDAO;
import com.my.repairagency007.model.entity.User;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.my.repairagency007.util.MapperDTOUtil.convertDTOToUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private final UserDAO userDAO = mock(UserDAO.class);
    private final UserService userService = new UserServiceImpl(userDAO);

    @Test
    void testCorrectCreate() throws DAOException {
        when(userDAO.create(isA(User.class))).thenReturn(true);
        UserDTO userDTO = getTestUserDTO();
        assertDoesNotThrow(() -> userService.create(userDTO));
    }
    @Test
    void testBadCreate() throws DAOException {
        doThrow(new DAOException()).when(userDAO).create(isA(User.class));
        assertThrows(ServiceException.class, () -> {
            userService.create(getTestUserDTO());
        });
    }
    @Test
    void testCorrectUpdate() throws DAOException {
        UserDTO userDTO = getTestUserDTO();
        when(userDAO.update(isA(User.class))).thenReturn(convertDTOToUser(userDTO));
        assertDoesNotThrow(() -> userService.create(userDTO));
    }

    @Test
    void testBadUpdate() throws DAOException {
        doThrow(new DAOException()).when(userDAO).update(isA(User.class));
        assertThrows(ServiceException.class, () -> {
            userService.update(getTestUserDTO());
        });
    }

    @Test
    void testCorrectGetByEmail() throws DAOException {
        UserDTO userDTO = getTestUserDTO();
        User user = convertDTOToUser(userDTO);
        when(userDAO.getByEmail(isA(String.class))).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> userService.getByEmail("a1@aa.aaa"));
    }

    @Test
    void testBadGetByEmail() throws DAOException {
        doThrow(new DAOException()).when(userDAO).getByEmail(isA(String.class));
        assertThrows(ServiceException.class, () -> {
            userService.getByEmail("a1@aa.aaa");
        });
    }

    @Test
    void testCorrectGetNumberOfRecords() throws DAOException {
        when(userDAO.getNumberOfRecords(isA(String.class))).thenReturn(5);
        assertDoesNotThrow(() -> userService.getNumberOfRecords("filter"));
    }
    @Test
    void testBadGetNumberOfRecords() throws DAOException {
        doThrow(new DAOException()).when(userDAO).getNumberOfRecords(isA(String.class));
        assertThrows(ServiceException.class, () -> {
            userService.getNumberOfRecords("filter");
        });
    }

    @Test
    void testCorrectDelete() throws DAOException {
        when(userDAO.deleteById(1)).thenReturn(true);
        assertDoesNotThrow(() -> userService.delete(1));
    }

    @Test
    void testBadDelete() throws DAOException {
        doThrow(new DAOException()).when(userDAO).deleteById(1);
        assertThrows(ServiceException.class, () -> {
            userService.delete(1);
        });
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
