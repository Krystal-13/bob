package com.zerobase.bob.controller;

import com.zerobase.bob.dto.ReviewDto;
import com.zerobase.bob.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    @PostMapping()
    public ResponseEntity<ReviewDto> writeReview (Principal principal,
                                                  HttpServletRequest request,
                                                  @RequestPart(value = "dto") ReviewDto reviewDto,
                                                  @RequestPart(value = "file") MultipartFile file) {

        return ResponseEntity.ok(reviewService.writeReview(principal.getName(), reviewDto, file, request.getServletPath()));
    }
}
