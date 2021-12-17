package com.newrelic.mysqldemo;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ContentRestController {

    private final ContentRepository repository;

    public ContentRestController(ContentRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/content")
    public String createContent(@RequestBody String content) {
        Content created = new Content();
        created.setContent(content);
        Content saved = repository.save(created);
        return saved.getId().toString();
    }

    @GetMapping("/content/{id}")
    public String getContent(@PathVariable(name = "id") Integer id) {
        Optional<Content> returnValue = repository.findById(id);

        return returnValue.isPresent() ? returnValue.get().getContent() : "Not Found";
    }
}
