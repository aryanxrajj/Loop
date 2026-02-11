package com.example.loop.service;

import com.example.loop.model.Topic;
import com.example.loop.repository.TopicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository){
        this.topicRepository = topicRepository;
    }

    public List<Topic> getAllTopic(){
        return topicRepository.findAll();
    }

    public Topic getTopicById(Long id){
        return topicRepository.findById(id).orElse(null);
    }

    public Page<Topic> getTopicByPages(int page , int size){
        return topicRepository.findAll(PageRequest.of(page,size));
    }

    public Topic createTopic(Topic topic){return topicRepository.save(topic);}

    public Topic updateTopic(Long id, Topic topic){
        Topic existing = topicRepository.findById(id).orElse(null);
        if(existing != null){
            existing.setTitle(topic.getTitle());
            existing.setTopic(topic.getTopic());
            existing.setDescription(topic.getDescription());
            return topicRepository.save(existing);
        }
        return null;
    }

    public void deleteById(Long id){
        topicRepository.deleteById(id);
    }
}
