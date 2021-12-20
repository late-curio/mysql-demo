package com.newrelic.mysqldemo;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
public class ContentRestController {

    private final ContentRepository repository;
    private final ContentService service;

    public ContentRestController(ContentRepository repository, ContentService contentService) {
        this.repository = repository;
        this.service = contentService;
    }

    @PostMapping("/content")
    public String createContent(@RequestBody String content) {
        Content created = new Content();
        created.setContent(content);
        Content saved = repository.save(created);
        return saved.getId().toString();
    }

    @PostMapping("/manual")
    public String createContentManually(@RequestBody String content) throws IOException {
        Content created = new Content();
        created.setContent(content);
        Integer id = service.manualContentSave(content);
        return id.toString();
    }

    @GetMapping("/content/{id}")
    public String getContent(@PathVariable(name = "id") Integer id) {
        Optional<Content> returnValue = repository.findById(id);

        return returnValue.isPresent() ? returnValue.get().getContent() : "Not Found";
    }
}
