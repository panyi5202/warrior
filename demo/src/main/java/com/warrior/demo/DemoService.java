package com.warrior.demo;

import com.warrior.framework.annotation.Service;

/**
 * @author panyi on 18-7-19.
 */
@Service
public class DemoService {
    public Demo getDemo() {
        Demo demo = new Demo();
        demo.setName("勇士");
        demo.setDesc("宇宙第一强队");
        return demo;
    }
}
