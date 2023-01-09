package com.my.repairagency007.model.services.impl;

import com.my.repairagency007.DTO.FeedbackDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.DAO.FeedbackDAO;
import com.my.repairagency007.model.services.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger log = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private final FeedbackDAO feedbackDAO;

    public FeedbackServiceImpl (FeedbackDAO feedbackDAO) {
        this.feedbackDAO = feedbackDAO;
    }


    @Override
    public FeedbackDTO getById(int id) throws ServiceException {
        return null;
    }

    @Override
    public List<FeedbackDTO> getAll(String query) throws ServiceException {
        return null;
    }

    @Override
    public void update(FeedbackDTO entity) throws ServiceException {

    }

    @Override
    public void delete(int id) throws ServiceException {

    }
}
