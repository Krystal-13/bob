package com.zerobase.bob.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    @PostMapping()
    public ResponseEntity<ReviewDto> writeReview (Principal principal,
                                                  @RequestPart(value = "dto") ReviewDto reviewDto,
                                                  @RequestPart(value = "file") MultipartFile file) {

        return ResponseEntity.ok(reviewService.writeReview(principal.getName(), reviewDto, file));
    }
}
