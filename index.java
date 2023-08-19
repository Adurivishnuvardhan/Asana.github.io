

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsanaAirtableIntegrationService {

    private static final String ASANA_API_KEY = "YOUR_ASANA_API_KEY";
    private static final String AIRTABLE_API_KEY = "YOUR_AIRTABLE_API_KEY";
    private static final String AIRTABLE_BASE_ID = "YOUR_AIRTABLE_BASE_ID";

    public static void main(String[] args) {
        // Simulate a new task created in Asana
        Task newTask = new Task("123", "Sample Task", "John Doe", "2023-08-31", "This is a sample task created in Asana.");

        try {
            // Copy the task to Airtable
            copyTaskToAirtable(newTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyTaskToAirtable(Task task) throws Exception {
        // Create Airtable record data
        String airtableData = "{\"fields\": {"
            + "\"Task ID\": \"" + task.getId() + "\","
            + "\"Name\": \"" + task.getName() + "\","
            + "\"Assignee\": \"" + task.getAssignee() + "\","
            + "\"Due Date\": \"" + task.getDueDate() + "\","
            + "\"Description\": \"" + task.getDescription() + "\""
            + "}}";

        // Airtable API endpoint
        String airtableUrl = "https://api.airtable.com/v0/" + AIRTABLE_BASE_ID + "/Tasks";
        
        URL url = new URL(airtableUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + AIRTABLE_API_KEY);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = airtableData.getBytes();
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Task copied to Airtable successfully");
        } else {
            System.out.println("Failed to copy task to Airtable. Response code: " + responseCode);
        }
    }
}

class Task {
    private String id;
    private String name;
    private String assignee;
    private String dueDate;
    private String description;

    public Task(String id, String name, String assignee, String dueDate, String description) {
        this.id = id;
        this.name = name;
        this.assignee = assignee;
        this.dueDate = dueDate;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }
}


