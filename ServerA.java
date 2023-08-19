import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class ServerAApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerAApplication.class, args);
    }

    private final String serverBWebhookUrl = "http://server_b/webhook";

    @PostMapping("/data")
    public ResponseEntity<String> receiveData(@RequestBody String newData) {
        // Simulated processing on Server A
        System.out.println("Received data on Server A: " + newData);

        // Forward the data to Server B using a webhook
        forwardToServerB(newData);

        return ResponseEntity.ok("Data received and forwarded to Server B");
    }

    private void forwardToServerB(String data) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(serverBWebhookUrl, data, String.class);

        if (response.getStatusCodeValue() == 200) {
            System.out.println("Data forwarded to Server B successfully");
        } else {
            System.out.println("Failed to forward data to Server B");
        }
    }
}
