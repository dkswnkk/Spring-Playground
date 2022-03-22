package springboot.MVCBasic.basic.requesstmapping;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    @GetMapping()
    public String user() {
        return "get User";
    }

    @PostMapping()
    public String addUser() {
        return "post User";
    }

    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId) {
        return userId;
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId) {
        return userId;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return userId;
    }


}
