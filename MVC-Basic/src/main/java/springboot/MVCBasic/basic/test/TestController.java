package springboot.MVCBasic.basic.test;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springboot.MVCBasic.basic.HelloData;
import springboot.MVCBasic.basic.TestData;

@Controller
public class TestController {


    @ResponseBody
    @PostMapping("test/request-param")
    public TestData requestParam(@RequestParam String name,
                                 @RequestParam int age) {
        TestData testData = new TestData();
        testData.setName(name);
        testData.setAge(age);
        return testData;
    }

    @ResponseBody
    @PostMapping("/test/request-body")
    public TestData requestBody(@RequestBody TestData testData) {
        return testData;
    }

    @ResponseBody
    @PostMapping("/test/model-attribute/param")
    public TestData modelAttributeParam(@ModelAttribute TestData testData) {
        return testData;
    }

    @ResponseBody
    @PostMapping("/test/model-attribute/body")
    public TestData modelAttributeBody(@ModelAttribute TestData testData) {
        return testData;
    }
}
