import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ChatBot {

    private static final Map<String, String> rules = new LinkedHashMap<>();

    static {
        rules.put("(?i).*(hello|hi|hey).*", "Hey! How can I help you?");
        rules.put("(?i).*how are you.*", "I'm doing good ! How about you?");
        rules.put("(?i).*(name|who are you).*", "I'm a simple chatbot built using rule-based logic.");
        rules.put("(?i).*(help|commands).*", "You can greet me, ask for time/date, or type 'bye' to exit.");
        rules.put("(?i).*time.*", getTime());
        rules.put("(?i).*date.*", getDate());
        rules.put("(?i).*(bye|exit|quit).*", "Bye! Have a great day ");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Chatbot started. Type something (type 'bye' to exit)");
 
        while (true) {
            System.out.print("You: ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) continue;

            String response = getResponse(input);
            System.out.println("Bot: " + response);

            if (Pattern.matches("(?i).*(bye|exit|quit).*", input)) {
                break;
            }
        }

        sc.close();
    }

    private static String getResponse(String input) {
        for (Map.Entry<String, String> entry : rules.entrySet()) {
            if (Pattern.matches(entry.getKey(), input)) {
                return entry.getValue();
            }
        }
        return "I didn't understand that. Try typing 'help'.";
    }

    private static String getTime() {
        return "Current time: " + java.time.LocalTime.now().withSecond(0).withNano(0);
    }

    private static String getDate() {
        return "Today's date: " + java.time.LocalDate.now();
    }
}