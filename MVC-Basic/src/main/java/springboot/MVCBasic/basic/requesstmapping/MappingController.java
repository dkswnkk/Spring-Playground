package springboot.MVCBasic.basic.requesstmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MappingController {

    @GetMapping({"/hello-basic", "/hello-go"})
    public String helloBasic() {
        log.info("helloBasic || hello-go onClick");
        return "helloBasic || hello-go";
    }

//   @RequestMapping(value = {"/hello-basic","hello-go"},method = RequestMethod.POST) // method속성을 지정해주지 않을시 메서드와 무관하게 호출된다.
//    public String helloBasic() {
//        log.info("helloBasic || hello-go onClick");
//        return "helloBasic || hello-go";
//    }

//    @GetMapping("/mapping/{userId}/{userName}")
//    public String mappingPath(@PathVariable("userId") String id,
//                              @PathVariable("userName") String name){
//        log.info("mappingPath userId = {} ", id);
//        log.info("mappingPath userId = {} ", name);
//        return "굿";
//    }

    @GetMapping(value = "/mapping/{userId}/{userName}")
    public String mappingPath(@PathVariable String userId, @PathVariable String userName){
        log.info("mappingPath userId = {} ", userId);
        log.info("mappingPath userId = {} ", userName);
        return "굿";
    }



}
