package com.vshtd.parceldelivery.logistic.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SwaggerController {

    @Operation(summary = "swagger redirect controller", hidden = true)
    @RequestMapping("/logistic/swagger-ui/index.html")
    void swagger(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui/index.html");
    }
}