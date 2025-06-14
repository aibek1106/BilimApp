package kg.bilim_app.mobile_api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/meta")
public class MetaController {

    @Value("${app.version}")
    private String version;

    @GetMapping("/version")
    public Map<String, String> getVersion() {
        return Map.of("version", version);
    }
}
