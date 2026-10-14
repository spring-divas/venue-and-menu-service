package org.spring.divas.venue.feature.menu;

import org.spring.divas.venue.feature.dish.DishResponseDto;

import java.util.List;

public interface MenuHtmlService {
    String render(String title, List<DishResponseDto> dishes);
}
