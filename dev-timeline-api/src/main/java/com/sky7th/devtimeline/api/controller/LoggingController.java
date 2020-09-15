package com.sky7th.devtimeline.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {

  @GetMapping("/health")
  public ResponseEntity<Void> checkHealth() {
    return ResponseEntity.ok().build();
  }
}
