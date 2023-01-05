package com.my.repairagency007.model.services.impl;

import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.DAO.RequestDAO;
import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.DAO.implementations.UserDAOImpl;
import com.my.repairagency007.model.entity.Request;
import com.my.repairagency007.model.services.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

import static com.my.repairagency007.util.ValidatorUtil.validateDescription;

public class RequestServiceImpl implements RequestService {

    private static final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final RequestDAO requestDAO;

    public RequestServiceImpl(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
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
    public RequestDTO getById(String idString) throws ServiceException {
        return null;
    }

    @Override
    public List<RequestDTO> getAll() throws ServiceException {
        return null;
    }

    @Override
    public void update(RequestDTO entity) throws ServiceException {

    }

    @Override
    public void delete(String idString) throws ServiceException {

    }

    public static Request convertDTOToRequest(RequestDTO requestDTO) {
        return Request.builder()
                .user_id(requestDTO.getUser_id())
                .description(requestDTO.getDescription())
                .date(LocalDate.parse(requestDTO.getDate()))
                .build();
    }
}
