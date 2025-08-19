package com.TFG.app.backend.category;

import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.user.entity.User;

public class CategoryTestDataBuilder {
    private String name = "Videogames";
    private String icon = "game_controller";
    private User user;

    public CategoryTestDataBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public CategoryTestDataBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Category build() {
        Category category = new Category();
        category.setName(name);
        category.setIcon(icon);
        category.setUser(user);
        return category;
    }
}
