package com.my.repairagency007.model.services;

import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.entity.Request;

import java.util.List;

public interface RequestService extends Service<RequestDTO>{

    Request addRequest(RequestDTO requestDTO) throws ServiceException;
    List<RequestDTO> getAllForCraftsman(String query, int userId) throws ServiceException;
    List<RequestDTO> getAllForUser(String query, int userId) throws ServiceException;
    List<RequestDTO> getByReparierId(String query, int repairerId) throws ServiceException;
    boolean setPaymentStatusPaid(int requestId) throws ServiceException;

}
