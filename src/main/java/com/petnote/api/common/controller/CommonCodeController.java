package com.petnote.api.common.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Transactional
@RequiredArgsConstructor
@RestController
@RequestMapping("/common")
@Validated
public class CommonCodeController {

}
