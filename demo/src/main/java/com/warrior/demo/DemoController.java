package com.warrior.demo;

import com.warrior.framework.annotation.Action;
import com.warrior.framework.annotation.Controller;
import com.warrior.framework.annotation.Inject;
import com.warrior.framework.bean.Data;
import com.warrior.framework.bean.Param;
import com.warrior.framework.bean.View;
import org.apache.commons.lang3.StringUtils;

/**
 * @author panyi on 18-7-19.
 */
@Controller
public class DemoController {
    @Inject
    private DemoService demoService;

    @Action("get:/demo")
    public View demoPage(Param param) {
        Demo demo = demoService.getDemo();
        // 获取get请求参数
        String name = param.getString("name");
        if (StringUtils.isNotBlank(name)) {
            demo.setName(name);
        }
        // 返回jsp
        return new View("demo.jsp").addModel("demo", demo);
    }

    @Action("get:/demo/data")
    public Data demoData(Param param) {
        Demo demo = demoService.getDemo();
        // 返回json串
        return new Data(demo);
    }

    @Action("get:/demo/add/page")
    public View addpage(Param param){
        return new View("demoAdd.jsp");
    }
    @Action("post:/demo/add")
    public View demoAdd(Param param){
        String name = param.getString("name");
        String desc = param.getString("desc");

        Demo demo = new Demo();
        demo.setName(name);
        demo.setDesc(desc);
        // 返回jsp
        return new View("demo.jsp").addModel("demo", demo);
    }
}
