package org.example.expert.domain.search.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.search.dto.response.TodoSearchResponse;
import org.example.expert.domain.search.service.SerchService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class searchController {

    private final SerchService serchService;

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<TodoSearchResponse>> searchTodo(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String nickname
    ){
        return ResponseEntity.ok(serchService.execute(page, size, title, nickname));
    }
}
