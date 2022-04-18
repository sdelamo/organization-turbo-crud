package com.example.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import java.net.URI;

@Controller
public class HomeController {
    @Get
    HttpResponse<?> index() {
        return HttpResponse.seeOther(URI.create(OrganizationController.PREFIX));
    }
}
