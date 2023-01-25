package com.my.repairagency007.model.services.impl;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.DAO.RequestDAO;
import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.DAO.UserDAO;
import com.my.repairagency007.model.entity.CompletionStatus;
import com.my.repairagency007.model.entity.Request;
import com.my.repairagency007.model.entity.User;
import com.my.repairagency007.model.services.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.my.repairagency007.util.MapperDTOUtil.*;
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
            if (!requestDAO.create(request)) {
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

        RequestDTO requestDTO;

        try {
            requestDTO = fillDTOFromRequestAndUsers(requestDAO.getEntityById(id));
        } catch (DAOException e) {
            log.error("Error getEntity by id method", e);
            throw new ServiceException(e);
        }
        return requestDTO;
    }

    public List<RequestDTO> getAllForCraftsman(String query, int userId) throws ServiceException {

        List<RequestDTO> requestDTOS = new ArrayList<>();
        try {
            log.debug("Try to execute requestDAO findAllForCraftsman method");
            List<Request> requests = requestDAO.findAllForCraftsman(query, userId);
            log.debug("convert request to dto");
            for (Request request : requests
            ) {
                requestDTOS.add(fillDTOFromRequestAndUsers(request));
            }
            log.debug("All OK inside getAllForUser Request");
        } catch (DAOException e) {
            log.error("Error to find request and form DTO");
            throw new ServiceException(e);
        }

        return requestDTOS;
    }

    public List<RequestDTO> getAllForUser(String query, int userId) throws ServiceException {

        List<RequestDTO> requestDTOS = new ArrayList<>();
        try {
            log.debug("Try to execute requestDAO findAllForUser method");
            List<Request> requests = requestDAO.findAllForUser(query, userId);
            log.debug("convert request to dto");
            for (Request request : requests
            ) {
                requestDTOS.add(fillDTOFromRequestAndUsers(request));
            }
            log.debug("All OK inside getAllForUser Request");
        } catch (DAOException e) {
            log.error("Error to find request and form DTO");
            throw new ServiceException(e);
        }
        return requestDTOS;
    }

    @Override
    public List<RequestDTO> getAll(String query) throws ServiceException {
        List<RequestDTO> requestDTOS = new ArrayList<>();
        try {
            log.debug("Try to execute requestDAO findAll method");
            List<Request> requests = requestDAO.findAll(query);
            log.debug("convert request to dto");
            for (Request request : requests
            ) {
                requestDTOS.add(fillDTOFromRequestAndUsers(request));
            }
            log.debug("All OK inside getAll Request");
        } catch (DAOException e) {
            log.error("Error to find request and form DTO");
            throw new ServiceException(e);
        }
        return requestDTOS;
    }

    public List<RequestDTO> getByReparierId(String query, int repairerId) throws ServiceException {

        List<RequestDTO> requestDTOS = new ArrayList<>();
        String addQuery = null;
        if (repairerId == 0) {
            addQuery = " where repairer_id=nullif()" + query;
        } else {
            addQuery = " where repairer_id=" + repairerId + query;
        }
        try {
            log.debug("Try to execute requestDAO findAll method with addQuery = " + addQuery);
            List<Request> requests = requestDAO.findAll(addQuery);
            log.debug("convert request to dto");
            for (Request request : requests
            ) {
                requestDTOS.add(fillDTOFromRequestAndUsers(request));
            }
            log.debug("All OK inside getAll Request");
        } catch (DAOException e) {
            log.error("Error to find request and form DTO");
            throw new ServiceException(e);
        }
        return requestDTOS;
    }

    public boolean setPaymentStatusPaid(int requestId) throws ServiceException {

        boolean result;
        Request request = null;

        try {
            request = requestDAO.getEntityById(requestId);
            User user = userDAO.getEntityById(request.getUser_id());
            result = requestDAO.setPaymentStatusPaid(request, user);
        } catch (DAOException e) {
            log.error("Error to Set Payment PAID");
            throw new ServiceException(e);
        }
        return result;
    }

    public void setPaymentStatusCanceled(int requestId) throws ServiceException {

        Request request = null;
        try {
            request = requestDAO.getEntityById(requestId);
            requestDAO.setPaymentStatusCanceled(request);
        } catch (DAOException e) {
            log.error("Error to Set Payment CANCELED");
            throw new ServiceException(e);
        }
    }

    public List<RequestDTO> getSorted(String query) throws ServiceException {
        return null;
    }


    public int getNumberOfUserRecords(String filter, int userId) throws ServiceException {
        int records;
        try {
            records = requestDAO.getNumberOfUserRecords(filter, userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return records;
    }

    public int getNumberOfRecords(String filter) throws ServiceException {
        int records;
        try {
            records = requestDAO.getNumberOfRecords(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return records;
    }

    @Override
    public void update(RequestDTO requestDTO) throws ServiceException {
        log.debug("Update request. RequestDTO = " + requestDTO);
        Request request = convertDTOToRequest(requestDTO);
        if (requestDTO.getId() != 0) {
            request.setId(requestDTO.getId());
        }
        try {
            requestDAO.update(request);
        } catch (DAOException e) {
            log.error("Error update user", e);
            throw new ServiceException(e);
        }
    }

    public void updateRepairForRequest(RequestDTO requestDTO) throws ServiceException {

        Request request = convertDTOToRequest(requestDTO);
        if (requestDTO.getId() != 0) {
            request.setId(requestDTO.getId());
        }
        try {
            requestDAO.updateRepairForRequest(request);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public void create(RequestDTO requestDTO) throws ServiceException {

        Request req = convertDTOToRequest(requestDTO);
        try {
            requestDAO.create(req);
        } catch (DAOException e) {
            log.error("Error create request", e);
            throw new ServiceException(e);
        }
    }

    private RequestDTO fillDTOFromRequestAndUsers(Request request) throws DAOException {
        log.debug("take user by id");
        User user = userDAO.getEntityById(request.getUser_id());
        log.debug("take repairer by id " + request.getRepairer_id());
        User repairer = null;
        RequestDTO requestDTO = convertRequestToDTO(request, user);
        log.debug("Fill requestDTO = " + requestDTO);
        int repairer_id = request.getRepairer_id();
        if (repairer_id != 0) {
            repairer = userDAO.getEntityById(repairer_id);
            requestDTO.setRepairer_id(request.getRepairer_id());
            requestDTO.setRepairerFirstName(repairer.getFirstName());
            requestDTO.setRepairerLastName(repairer.getLastName());
        }
        log.debug("Convert request to DTO");
        return requestDTO;
    }

    public void setStartRepair(RequestDTO requestDTO, UserDTO currentUser) throws ServiceException {

        Request request = convertDTOToRequest(requestDTO);
        log.debug("Try to set status in progress");
        request.setCompletionStatusId((CompletionStatus.IN_PROGRESS.ordinal() + 1));
        log.debug("Set Completion status 'in progress' = " + request.getCompletionStatusId());
        request.setRepairer_id(currentUser.getId());
        log.debug("Set repair id = " + request.getRepairer_id());
        try {
            requestDAO.setStartRepair(request);
        } catch (DAOException e) {
            log.error("Error set start repair");
            throw new ServiceException(e);
        }
    }

    public void setCompletedRepair(RequestDTO requestDTO) throws ServiceException {

        Request request = convertDTOToRequest(requestDTO);
        log.debug("Try to set status completed");
        request.setCompletionStatusId((CompletionStatus.COMPLETED.ordinal() + 1));
        log.debug("Set Completion status 'completed' = " + request.getCompletionStatusId());
        log.debug("Set repair id = " + request.getRepairer_id());
        try {
            requestDAO.setStartRepair(request);
        } catch (DAOException e) {
            log.error("Error set completed repair");
            throw new ServiceException(e);
        }
    }
}


