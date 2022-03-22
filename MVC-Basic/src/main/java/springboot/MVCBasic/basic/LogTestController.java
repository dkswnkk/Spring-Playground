package springboot.MVCBasic.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {

    @GetMapping("log-test")
    public String logTest() {

        String name = "Spring";
        log.trace("trace log" + name);  // 이런 식으로 사용하면 안됨.
        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

        return "ok";
    }

}
