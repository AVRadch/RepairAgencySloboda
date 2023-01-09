package com.my.repairagency007.model.services.impl;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.DAO.RequestDAO;
import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.DAO.UserDAO;
import com.my.repairagency007.model.entity.Request;
import com.my.repairagency007.model.entity.User;
import com.my.repairagency007.model.services.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.my.repairagency007.util.MapperDTOUtil.convertDTOToRequest;
import static com.my.repairagency007.util.MapperDTOUtil.convertRequestToDTO;
import static com.my.repairagency007.util.ValidatorUtil.validateDescription;

public class RequestServiceImpl implements RequestService {

    private static final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final RequestDAO requestDAO;

    private final UserDAO userDAO;

    public RequestServiceImpl(RequestDAO requestDAO, UserDAO userDAO) {
        this.requestDAO = requestDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Request addRequest(RequestDTO requestDTO) throws ServiceException {
        log.debug("Start add request. Validate Description");
        validateDescription(requestDTO.getDescription());
        log.debug("convert request DTO to request");
        Request request = convertDTOToRequest(requestDTO);

        try {
            log.debug("try to create request");
            if (!requestDAO.create(request)){
                log.error("error create request");
                throw new ServiceException();
            }
        } catch (DAOException e) {
            log.error("error create reuqest", e);
            throw new ServiceException(e);
        }
        return request;
    }

    @Override
    public List<RequestDTO> viewCompletionStatusRequests(String completionStatus) throws ServiceException {
        return null;
    }

    @Override
    public List<RequestDTO> viewPaymentStatusRequests(String paymentStatus) throws ServiceException {
        return null;
    }

    @Override
    public void setCraftsman(int requestId, int userId) throws ServiceException {

    }

    @Override
    public void setTotalCost(int requestId, int totalCost) throws ServiceException {

    }

    @Override
    public void setPaymentStatus(int requestId, String paymentStatus) throws ServiceException {

    }

    @Override
    public void setCompletionStatus(int requestId, String completionStatus) throws ServiceException {

    }

    @Override
    public RequestDTO getById(int id) throws ServiceException {
        return null;
    }

    @Override
    public List<RequestDTO> getAll(String query) throws ServiceException {
        List<RequestDTO> requestDTOS = new ArrayList<>();
        try {
            log.trace("Try to execute requestDAO findAll method");
            List<Request> requests = requestDAO.findAll(query);
            log.trace("convert request to dto");
            for (Request request: requests
                 ) {
                User user = userDAO.getEntityById(request.getUser_id());
                User repairer = userDAO.getEntityById(request.getRepairer_id());
                requestDTOS.add(convertRequestToDTO(request, user, repairer));
            }
        } catch (DAOException e) {
            log.error("Error to find request and form DTO");
            throw new ServiceException(e);
        }
        for (RequestDTO  requestDTO: requestDTOS
             ) {
            log.debug("requestDTO" + requestDTO);
        }
        return requestDTOS;
    }

    public List<RequestDTO> getSorted(String query) throws ServiceException {
        return null;
    }

    public int getNumberOfRecords(String filter) throws ServiceException {
        return 0;
    }

    @Override
    public void update(RequestDTO request) throws ServiceException {

    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            log.debug("try to delete request");
            requestDAO.deleteById(id);
        } catch (DAOException e) {
            log.error("Error delete request", e);
            throw new ServiceException(e);
        }
    }

}
