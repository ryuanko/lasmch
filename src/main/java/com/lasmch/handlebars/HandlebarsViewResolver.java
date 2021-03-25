//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lasmch.handlebars;

import com.github.jknack.handlebars.Handlebars;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class HandlebarsViewResolver extends AbstractTemplateViewResolver {
    final Handlebars handlebars;

    public HandlebarsViewResolver(Handlebars handlebars) {
        this.handlebars = handlebars;
        this.setContentType("text/html;charset=UTF-8");
        this.setViewClass(this.requiredViewClass());
    }

    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        if (viewName == null || viewName.trim().isEmpty()) {
            viewName = "index";
        }

        HandlebarsView view = (HandlebarsView)super.buildView(viewName);
        view.template = this.handlebars.compile(viewName);
        return view;
    }

    protected Class<?> requiredViewClass() {
        return HandlebarsView.class;
    }

    public HandlebarsViewResolver registerHelpers(final Object helper) {
        this.handlebars.registerHelpers(helper);
        return this;
    }
}
