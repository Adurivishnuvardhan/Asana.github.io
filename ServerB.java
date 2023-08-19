import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class ServerBApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerBApplication.class, args);
    }

    private List<String> serverBData = new ArrayList<>();

    @PostConstruct
    public void init() {
        System.out.println("Server B started.");
    }

    @PostMapping("/webhook")
    public String receiveWebhookData(@RequestBody String newData) {
        // Simulated processing on Server B
        System.out.println("Received webhook data on Server B: " + newData);
        serverBData.add(newData);
        return "Webhook data received";
    }
}
