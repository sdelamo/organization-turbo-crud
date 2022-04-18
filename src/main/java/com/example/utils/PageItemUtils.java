package com.example.utils;

import com.example.views.PageItem;
import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.data.model.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class PageItemUtils {

    public static List<PageItem> of(LocalizedMessageSource localizedMessageSource,
                                    Function<Integer, String> pageUriBuilder,
                                    Page<?> page) {
        List<PageItem> result = new ArrayList<>();
        if (page.getPageNumber() > 0) {
            result.add(pageItem(pageUriBuilder,
                page.getPageNumber() - 1,
                localizedMessageSource.getMessageOrDefault("pagination.previous", "Previous"), false));
        }
        for (int i = 0; i < page.getTotalPages(); i++) {
            result.add(pageItem(pageUriBuilder, i, "" + (i + 1), (i == page.getPageNumber())));
        }
        if (page.getPageNumber() < (page.getTotalPages() - 1)) {
            result.add(pageItem(pageUriBuilder,
                page.getPageNumber() + 1,
                localizedMessageSource.getMessageOrDefault("pagination.next", "Next"), false));
        }
        return result;
    }

    private static PageItem pageItem(Function<Integer, String> pageUriBuilder,
                                     int number,
                                     String message,
                                     boolean active) {
        return new PageItem(pageUriBuilder.apply(number), message, active);
    }
}
