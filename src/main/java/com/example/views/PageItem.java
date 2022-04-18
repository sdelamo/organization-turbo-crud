package com.example.views;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

public class PageItem {
    @NonNull
    @NotBlank
    private final String href;

    @NonNull
    @NotBlank
    private final String title;

    private final boolean active;

    public PageItem(@NonNull String href, @NonNull String title, boolean active) {
        this.href = href;
        this.title = title;
        this.active = active;
    }

    public PageItem(@NonNull String href, @NonNull String title) {
        this(href, title, false);
    }

    @NonNull
    public String getHref() {
        return href;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public boolean isActive() {
        return active;
    }
}
