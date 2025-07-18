package com.app.news.news.http.rest;

import org.springframework.web.bind.annotation.RestController;

import com.app.news.news.client.AuthClient;
import com.app.news.news.dto.NewsCreateDto;
import com.app.news.news.dto.NewsPutDto;
import com.app.news.news.dto.NewsReadDto;
import com.app.news.news.dto.UserReadDto;
import com.app.news.news.service.NewsService;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequiredArgsConstructor
public class NewsRestController {

    private final NewsService newsService;
    private final AuthClient authClient;
    
    @GetMapping("api/v1/public/news")
    public List<NewsReadDto> findAll() {
        return newsService.findAll();
    }

    @GetMapping("api/v1/public/news/{id}")
    public NewsReadDto findById(@PathVariable Long id) {
        return newsService.findById(id);
    }

    @PostMapping("api/v1/news")
    public ResponseEntity<?> create(@RequestBody NewsCreateDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        UserReadDto userDto = authClient.fetchUserById(dto.getUserId());
        if (!userDto.getId().equals(dto.getUserId())) {
            return status(403).build();
        }
        return ok(newsService.create(dto));
    }

    @PutMapping("api/v1/{id}")
    public ResponseEntity<?> put(
        @RequestBody @Validated NewsPutDto newsPutDto, 
        @PathVariable Long id, 
        @AuthenticationPrincipal UserDetails userDetails) {
        
        NewsReadDto news = newsService.findById(id);

        if (!news.getUser().getUsername().equals(userDetails.getUsername())) {
            return status(403).build();
        }

        return status(204).body(newsService.put(newsPutDto, id));
    }

    @DeleteMapping("api/v1/news/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (!newsService.findById(id).getUser().getUsername()
                .equals(userDetails.getUsername())) {
            return status(403).build();
        }
        return newsService.delete(id)
            ? noContent().build()
            : badRequest().build();
    }
    
}
