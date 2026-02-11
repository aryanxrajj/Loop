package com.example.loop.controller;

import com.example.loop.model.Topic;
import com.example.loop.service.TopicService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin(value = "*")
public class TopicController {
    private TopicService topicService;

    public TopicController(TopicService topicService){
        this.topicService = topicService;
    }

    @GetMapping
    public List<Topic> getAllTopic(){
        return topicService.getAllTopic();
    }

    @GetMapping("/{id}")
    public Topic getTopicById(@PathVariable Long id){
        return topicService.getTopicById(id);
    }

    @GetMapping("/page")
    public List<Topic> getTopicByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return topicService.getTopicByPages(page,size).getContent();
    }

    @PostMapping
    public Topic createTopic(@RequestBody Topic topic){
        return topicService.createTopic(topic);
    }

    @PutMapping("/{id}")
    public Topic updateTopic(@PathVariable Long id , @RequestBody Topic topic){
        return topicService.updateTopic(id,topic);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        topicService.deleteById(id);
    }
}
