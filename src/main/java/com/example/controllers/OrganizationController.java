package com.example.controllers;

import com.example.entities.OrganizationEntity;
import com.example.repositories.OrganizationRepository;
import com.example.utils.PageItemUtils;
import com.example.views.PageItem;
import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.views.View;
import io.micronaut.views.turbo.TurboStream;
import io.micronaut.views.turbo.http.TurboMediaType;
import javax.validation.constraints.Min;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller(OrganizationController.PREFIX)
public class OrganizationController {
    public static final String PREFIX = "/organizations";

    private final LocalizedMessageSource localizedMessageSource;
    private final OrganizationRepository organizationRepository;

    public OrganizationController(LocalizedMessageSource localizedMessageSource,
                                  OrganizationRepository organizationRepository) {
        this.localizedMessageSource = localizedMessageSource;
        this.organizationRepository = organizationRepository;
    }

    @Produces(value = {MediaType.TEXT_HTML})
//TODO uncomment for 3.4.0 of micronaut-views-core
// @TurboFrameView("/organization/_table.html")
    @View("/organization/index.html")
    @Get
    Map<String, Object> index(@QueryValue(defaultValue = "0") @Min(0) Integer page) {

        Pageable pageable = Pageable.from(page);
        Page<?> p = organizationRepository.findAll(pageable);
        List<PageItem> pages = PageItemUtils.of(localizedMessageSource,
            pageNumber -> UriBuilder.of(PREFIX).queryParam("page", pageNumber).build().toString(),
            p);
        return Map.of("organizations", p.getContent(), "pages", pages);
    }

    @Produces(value = {MediaType.TEXT_HTML})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post
    HttpResponse<?> save(@Body("name") String name,
                         HttpRequest<?> request) {
        OrganizationEntity organization = organizationRepository.save(name);
        if (TurboMediaType.acceptsTurboStream(request)) {
            return HttpResponse.ok(TurboStream.builder()
                .targetDomId("organizations-rows")
                .template("/organization/_tr.html", Collections.singletonMap("organization", organization))
                .append());
        }
        return HttpResponse.seeOther(URI.create(PREFIX));
    }

    @Produces(value = {MediaType.TEXT_HTML})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post("/{id}/delete")
    HttpResponse<?> delete(@PathVariable Long id,
                           HttpRequest<?> request) {
        organizationRepository.deleteById(id);
        if (TurboMediaType.acceptsTurboStream(request)) {
            return HttpResponse.ok(TurboStream.builder()
                .targetDomId("organization-" + id)
                .remove());
        }
        return HttpResponse.seeOther(URI.create(PREFIX));
    }
}
