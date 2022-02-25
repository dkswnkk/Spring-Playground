package basic.basic.web;

import basic.basic.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    private final MyLogger myLogger;

    public void logic(String Id) {
        myLogger.log("service id: " + Id);
    }
}
