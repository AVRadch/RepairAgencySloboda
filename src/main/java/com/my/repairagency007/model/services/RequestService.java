package com.my.repairagency007.model.services;

import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.entity.Request;

import java.util.List;

public interface RequestService extends Service<RequestDTO>{

    Request addRequest(RequestDTO requestDTO) throws ServiceException;

    List<RequestDTO> viewCompletionStatusRequests(String completionStatus) throws ServiceException;

    List<RequestDTO> viewPaymentStatusRequests(String paymentStatus) throws ServiceException;

    void setCraftsman(int requestId, int userId) throws ServiceException;

    void setTotalCost(int requestId, int totalCost) throws ServiceException;

    void setPaymentStatus(int requestId, String paymentStatus) throws ServiceException;

    void setCompletionStatus(int requestId, String completionStatus) throws ServiceException;
}
