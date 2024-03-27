package nwtprojekat.ArticleManagement.controller;

import jakarta.validation.Valid;
import nwtprojekat.ArticleManagement.model.Article;
import nwtprojekat.ArticleManagement.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @PostMapping("/add")
    public ResponseEntity<String> createArticle(@RequestBody Article article, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError titleError = bindingResult.getFieldError("title");
            if (titleError != null) {
                return ResponseEntity.badRequest().body(titleError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body("Invalid data: " + bindingResult.getAllErrors());
        }

        Article savedArticle = articleRepository.save(article);
        return ResponseEntity.ok("Article added successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(articles);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getArticleById(@PathVariable Long id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        return optionalArticle.map(article -> ResponseEntity.ok("Article successfully found"))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article with ID " + id + " not found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @Valid @RequestBody Article updatedArticle, BindingResult bindingResult) {
        if (!articleRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article with ID " + id + " not found");
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        updatedArticle.setId(id);
        Article savedArticle = articleRepository.save(updatedArticle);
        return ResponseEntity.ok(savedArticle);
    }

    @DeleteMapping("/remove/{id}")
    public boolean deleteArticle(@PathVariable Long id) {
        if (!articleRepository.existsById(id)) {
            return false;
        }
        articleRepository.deleteById(id);
        return true;
    }
}

