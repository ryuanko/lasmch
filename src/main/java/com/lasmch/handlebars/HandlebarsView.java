//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lasmch.handlebars;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.ValueResolver;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.AbstractTemplateView;

public class HandlebarsView extends AbstractTemplateView {
    protected Template template;

    public HandlebarsView() {
    }

    protected void renderMergedTemplateModel(Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) throws Exception {
        Context context = Context.newBuilder(map).resolver(ValueResolver.VALUE_RESOLVERS).build();

        try {
            this.template.apply(context, res.getWriter());
        } finally {
            context.destroy();
        }

    }
}
