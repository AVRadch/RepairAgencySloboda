package com.my.repairagency007.model.services.impl;

import com.my.repairagency007.DTO.FeedbackDTO;
import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.DAO.FeedbackDAO;
import com.my.repairagency007.model.DAO.UserDAO;
import com.my.repairagency007.model.entity.Feedback;
import com.my.repairagency007.model.entity.Request;
import com.my.repairagency007.model.entity.User;
import com.my.repairagency007.model.services.FeedbackService;
import com.my.repairagency007.model.services.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.my.repairagency007.util.MapperDTOUtil.*;

public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger log = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private final FeedbackDAO feedbackDAO;

    private final RequestService requestService;

    private final UserDAO userDAO;

    public FeedbackServiceImpl (FeedbackDAO feedbackDAO, UserDAO userDAO, RequestService requestService) {

        this.requestService = requestService;
        this.feedbackDAO = feedbackDAO;
        this.userDAO = userDAO;
    }


    @Override
    public FeedbackDTO getById(int id) throws ServiceException {
        return null;
    }

    @Override
    public List<FeedbackDTO> getAll(String query) throws ServiceException {

        List<FeedbackDTO> feedbackDTOS = new ArrayList<>();
        try {
            log.debug("Try to execute feedbackDAO findAll method");
            List<Feedback> feedbacks = feedbackDAO.findAll(query);
            log.debug("convert feedback to dto");
            for (Feedback feedback : feedbacks
            ) {
                RequestDTO request = requestService.getById(feedback.getRequestId());
                if (request == null){
                    log.trace("Request equal null" + feedback.getId());
                } else {
                                  log.trace("Request DTO for feedback -> " + request.getRepairer_id());
                }

                feedbackDTOS.add(convertFeedbackToDTO(feedback, request));
            }
        } catch (DAOException e) {
            log.error("Error to find request and form DTO");
            throw new ServiceException(e);
        }

        return feedbackDTOS;
    }

    @Override
    public void update(FeedbackDTO entity) throws ServiceException {

    }

    @Override
    public void create(FeedbackDTO feedbackDTO) throws ServiceException {

       Feedback feedback = convertDTOToFeedback(feedbackDTO);
        try {
            feedbackDAO.create(feedback);
        } catch (DAOException e) {
            log.error("Error create feedback", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(int id) throws ServiceException {

    }

    public int getNumberOfRecords(String filter) throws ServiceException {
        int records;
        try {
            records = feedbackDAO.getNumberOfRecords(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return records;
    }
}
