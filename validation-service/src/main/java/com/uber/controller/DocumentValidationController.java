package com.uber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = {"documents/validate"})
public class DocumentValidationController {

    @PostMapping
    public void validate(@RequestBody MultiValueMap<String, Object> documentsValidationRequest) {
        log.info("received validation request {} ", documentsValidationRequest);
    }

}
