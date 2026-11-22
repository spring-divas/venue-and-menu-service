package org.spring.divas.venue.feature.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.spring.divas.venue.feature.menu.MenuAssertions.assertHtml;
import static org.spring.divas.venue.feature.menu.MenuAssertions.assertMenuContext;
import static org.spring.divas.venue.feature.menu.MenuFixtures.categorizedMenu;
import static org.spring.divas.venue.feature.menu.MenuFixtures.css;
import static org.spring.divas.venue.feature.menu.MenuFixtures.cssResource;
import static org.spring.divas.venue.feature.menu.MenuFixtures.html;
import static org.spring.divas.venue.feature.menu.MenuFixtures.loadedCss;
import static org.spring.divas.venue.feature.menu.MenuFixtures.title;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@ExtendWith(MockitoExtension.class)
class MenuHtmlServiceImplTest {

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private Resource cssResource;

    private MenuHtmlServiceImpl menuHtmlService;

    @BeforeEach
    void setUp() {
        menuHtmlService = new MenuHtmlServiceImpl(templateEngine);
    }

    @Test
    void render_shouldGroupDishesByCategoryAndPutUncategorizedDishesInOther() {
        ReflectionTestUtils.setField(menuHtmlService, "css", css());
        when(templateEngine.process(org.mockito.ArgumentMatchers.eq("menu"), any(Context.class)))
                .thenReturn(html());
        String result = menuHtmlService.render(title(), categorizedMenu());

        ArgumentCaptor<Context> contextCaptor = ArgumentCaptor.forClass(Context.class);
        verify(templateEngine).process(org.mockito.ArgumentMatchers.eq("menu"), contextCaptor.capture());
        assertHtml(result);
        assertMenuContext(contextCaptor.getValue());
    }

    @Test
    void init_shouldLoadCssFromResource() {
        ReflectionTestUtils.setField(menuHtmlService, "cssResource", cssResource());
        menuHtmlService.init();
        assertThat(ReflectionTestUtils.getField(menuHtmlService, "css")).isEqualTo(loadedCss());
    }

    @Test
    void init_shouldWrapIoExceptionWhenCssCannotBeRead() throws IOException {
        ReflectionTestUtils.setField(menuHtmlService, "cssResource", cssResource);
        when(cssResource.getInputStream()).thenThrow(new IOException("CSS is unavailable"));
        assertThatThrownBy(menuHtmlService::init)
                .isInstanceOf(java.io.UncheckedIOException.class)
                .hasMessage("Failed to load CSS file")
                .hasCauseInstanceOf(IOException.class);
    }
}
