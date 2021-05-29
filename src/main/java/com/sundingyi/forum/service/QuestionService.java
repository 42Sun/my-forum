package com.sundingyi.forum.service;

import com.sundingyi.forum.dto.PaginationDTO;
import com.sundingyi.forum.dto.QuestionDTO;
import com.sundingyi.forum.mapper.QuestionMapper;
import com.sundingyi.forum.mapper.UserMapper;
import com.sundingyi.forum.model.Question;
import com.sundingyi.forum.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    
    final UserMapper userMapper;
    final QuestionMapper questionMapper;
    
    public QuestionService(UserMapper userMapper, QuestionMapper questionMapper) {
        this.userMapper = userMapper;
        this.questionMapper = questionMapper;
    }
    
    public PaginationDTO list(Integer page, Integer size) {
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestion(questionDTOS);
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);
        return paginationDTO;
    }
    
    public PaginationDTO list(Long id, Integer page, Integer size) {
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.listById(id, offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestion(questionDTOS);
        Integer totalCount = questionMapper.countById(id);
        paginationDTO.setPagination(totalCount, page, size);
        return paginationDTO;
    }
}
