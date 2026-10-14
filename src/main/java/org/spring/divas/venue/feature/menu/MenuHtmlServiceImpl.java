package org.spring.divas.venue.feature.menu;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.spring.divas.venue.feature.dish.DishResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuHtmlServiceImpl implements MenuHtmlService {
    private final TemplateEngine templateEngine;

    @Value("${menu.css.path}")
    private Resource cssResource;

    private String css;

    @PostConstruct
    public void init() {
        this.css = loadCssContent();
    }

    @Override
    public String render(String title, List<DishResponseDto> dishes) {
        Map<String, List<DishResponseDto>> dishesByCategory = groupDishesByCategory(dishes);
        Context context = buildTemplateContext(title, dishesByCategory);
        return templateEngine.process("menu", context);
    }

    private Map<String, List<DishResponseDto>> groupDishesByCategory(List<DishResponseDto> dishes) {
        return dishes.stream().collect(Collectors.groupingBy(
                dish -> dish.getCategory().getName(),
                LinkedHashMap::new,
                Collectors.toList()));
    }

    private Context buildTemplateContext(String title, Map<String, List<DishResponseDto>> dishesByCategory) {
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("dishesByCategory", dishesByCategory);
        context.setVariable("css", css);
        return context;
    }

    private String loadCssContent() {
        try (InputStream in = cssResource.getInputStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load CSS file", e);
        }
    }
}