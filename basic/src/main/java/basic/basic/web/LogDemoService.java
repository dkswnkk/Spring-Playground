package basic.basic.web;

import basic.basic.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    private final ObjectProvider<MyLogger> myLoggerProvider;

    public void logic(String Id) {
        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.log("service id: " + Id);
    }
}
