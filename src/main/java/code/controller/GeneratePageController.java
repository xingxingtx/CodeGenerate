package code.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wei.peng on 2018/9/4.
 */
@Controller()
public class GeneratePageController {

    @RequestMapping("/generators/index")
    public String toGeneratePage(){
        return "generatorIndex";
    }

}
