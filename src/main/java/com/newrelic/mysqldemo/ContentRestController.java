package com.newrelic.mysqldemo;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
public class ContentRestController {

    private final JpaContentRepository repository;
    private final JdbcContentService service;

    public ContentRestController(JpaContentRepository repository, JdbcContentService contentService) {
        this.repository = repository;
        this.service = contentService;
    }

    @PostMapping("/content/jpa-repository")
    public String createContent(@RequestBody String content) {
        Content created = new Content();
        created.setContent(content);
        Content saved = repository.save(created);
        return saved.getId().toString();
    }

    @PostMapping("/content/jdbc-statement")
    public String createContentManually(@RequestBody String content) throws IOException {
        int id = service.createContentViaStatementAndManualSqlConcatenation(content);
        return Integer.toString(id);
    }

    @PostMapping("/content/jdbc-prepared-statement")
    public String createContentWithPreparedStatement(@RequestBody String content) throws IOException {
        int id = service.createContentViaPreparedStatement(content);
        return Integer.toString(id);
    }

    @GetMapping("/content/{id}")
    public String getContent(@PathVariable(name = "id") Integer id) {
        Optional<Content> returnValue = repository.findById(id);
        return returnValue.isPresent() ? returnValue.get().getContent() : "Not Found";
    }
}
