package treehouse.server.api.root.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootApi {

    @GetMapping("/health")
    public String healthCheck(){
        return "I'm healthy!";
    }
}
