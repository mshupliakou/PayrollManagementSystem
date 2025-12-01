package com.project_agh.payrollmanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class responsible for handling basic navigation routes,
 * primarily the login page display.
 */
@Controller
public class HomeController {

    /**
     * Maps HTTP requests to the "/login" path.
     * This method is responsible for returning the view name associated with the login page.
     *
     * @return The logical view name ("index") that Spring Boot/Thymeleaf will resolve
     * to the actual template file (e.g., src/main/resources/templates/index.html).
     */
    @RequestMapping("/login")
    public String index() {
        return "index";
    }
}