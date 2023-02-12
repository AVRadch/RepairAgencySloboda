package com.my.repairagency007.model.services;

import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.DAO.RequestDAO;
import com.my.repairagency007.model.DAO.UserDAO;
import com.my.repairagency007.model.entity.Request;
import com.my.repairagency007.model.entity.User;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.my.repairagency007.CommonEntity.*;
import static com.my.repairagency007.util.MapperDTOUtil.convertDTOToRequest;
import static com.my.repairagency007.util.MapperDTOUtil.convertDTOToUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class RequestServiceTest {

    private final RequestDAO requestDAO = mock(RequestDAO.class);
    private final UserDAO userDAO = mock(UserDAO.class);
    private final RequestService requestService = new RequestServiceImpl(requestDAO, userDAO);

    @Test
    void testCorrectCreateRequest() throws DAOException {
        when(requestDAO.create(isA(Request.class))).thenReturn(true);
        RequestDTO requestDTO = getTestRequestDTO();
        assertDoesNotThrow(() -> requestService.addRequest(requestDTO));
    }
    @Test
    void testBadCreateRequest() throws DAOException {
        doThrow(new DAOException()).when(requestDAO).create(isA(Request.class));
        assertThrows(ServiceException.class, () -> {
            requestService.addRequest(getTestRequestDTO());
        });
    }
    @Test
    void testCorrectGetById() throws DAOException {
        Request request = convertDTOToRequest(getTestRequestDTO());
        User user = convertDTOToUser(getTestUserDTO());
        when(requestDAO.getEntityById(1)).thenReturn(Optional.of(request));
        when(userDAO.getEntityById(3)).thenReturn(Optional.of(user));
        when(userDAO.getEntityById(2)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> requestService.getById(1));
    }
    @Test
    void testBadGetById() throws DAOException {
        doThrow(new DAOException()).when(requestDAO).getEntityById(1);
        assertThrows(ServiceException.class, () -> {
            requestService.getById(1);
        });
    }
    @Test
    void testCorrectGetAll() throws DAOException {
        List<Request> requests = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            Request request = convertDTOToRequest(getTestRequestDTO());
            requests.add(request);
        }
        User user = convertDTOToUser(getTestUserDTO());
        when(requestDAO.findAll(isA(String.class))).thenReturn(requests);
        when(userDAO.getEntityById(3)).thenReturn(Optional.of(user));
        when(userDAO.getEntityById(2)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> requestService.getAll("1"));
    }
    @Test
    void testBadGetAll() throws DAOException {
        doThrow(new DAOException()).when(requestDAO).findAll("1");
        assertThrows(ServiceException.class, () -> {
            requestService.getAll("1");
        });
    }
    @Test
    void testCorrectAllForCraftsman() throws DAOException {
        List<Request> requests = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            Request request = convertDTOToRequest(getTestRequestDTO());
            requests.add(request);
        }
        User user = convertDTOToUser(getTestUserDTO());
        when(requestDAO.findAllForCraftsman("query", 1)).thenReturn(requests);
        when(userDAO.getEntityById(3)).thenReturn(Optional.of(user));
        when(userDAO.getEntityById(2)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> requestService.getAllForCraftsman("query", 1));
    }

    @Test
    void testBadAllForCraftsman() throws DAOException {
        doThrow(new DAOException()).when(requestDAO).findAllForCraftsman("query",1);
        assertThrows(ServiceException.class, () -> {
            requestService.getAllForCraftsman("query", 1);
        });
    }
    @Test
    void testCorrectAllForUser() throws DAOException {
        List<Request> requests = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            Request request = convertDTOToRequest(getTestRequestDTO());
            requests.add(request);
        }
        User user = convertDTOToUser(getTestUserDTO());
        when(requestDAO.findAllForUser("query", 1)).thenReturn(requests);
        when(userDAO.getEntityById(3)).thenReturn(Optional.of(user));
        when(userDAO.getEntityById(2)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> requestService.getAllForUser("query", 1));
    }

    @Test
    void testBadAllForUser() throws DAOException {
        doThrow(new DAOException()).when(requestDAO).findAllForUser("query", 1);
        assertThrows(ServiceException.class, () -> {
            requestService.getAllForUser("query", 1);
        });
    }
    @Test
    void testCorrectGetByRepairId() throws DAOException {
        List<Request> requests = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            Request request = convertDTOToRequest(getTestRequestDTO());
            requests.add(request);
        }
        User user = convertDTOToUser(getTestUserDTO());
        when(requestDAO.findAll("query")).thenReturn(requests);
        when(userDAO.getEntityById(3)).thenReturn(Optional.of(user));
        when(userDAO.getEntityById(2)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> requestService.getByReparierId("query", 1));
    }

    @Test
    void testBadGetByRepairId() throws DAOException {
        doThrow(new DAOException()).when(requestDAO).findAll(isA(String.class));
        assertThrows(ServiceException.class, () -> {
            requestService.getByReparierId("query", 1);
        });
    }
    @Test
    void testCorrectSetPaymentStatusPaid() throws DAOException {
        Request request = convertDTOToRequest(getTestRequestDTO());
        User user = convertDTOToUser(getTestUserDTO());
        when(requestDAO.getEntityById(1)).thenReturn(Optional.of(request));
        when(userDAO.getEntityById(3)).thenReturn(Optional.of(user));
        when(userDAO.getEntityById(2)).thenReturn(Optional.of(user));
        when(requestDAO.setPaymentStatusPaid(request, user)).thenReturn(true);
        assertDoesNotThrow(() -> requestService.setPaymentStatusPaid(1));
    }
    @Test
    void testCorrectSetPaymentStatusCanceled() throws DAOException {
        Request request = convertDTOToRequest(getTestRequestDTO());
        User user = convertDTOToUser(getTestUserDTO());
        when(requestDAO.getEntityById(1)).thenReturn(Optional.of(request));
        when(userDAO.getEntityById(3)).thenReturn(Optional.of(user));
        when(userDAO.getEntityById(2)).thenReturn(Optional.of(user));
        doNothing().when(requestDAO).setPaymentStatusCanceled(request);
        assertDoesNotThrow(() -> requestService.setPaymentStatusPaid(1));
    }
}