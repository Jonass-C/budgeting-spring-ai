package dio.jonas.budgeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "GEMINI_API_KEY", matches = ".+")
public class ToolCallingIT {

    @Autowired
    private ChatClient chatClient;

    static class MathTools {
        @Tool(description = "soma dois números inteiros, a e b")
        public int sum(int a, int b) {
            System.out.println("[TOOL RUN] Somando: " + a + " + " + b);
            return a + b;
        }

        @Tool(description = "subtrai dois números inteiros, a e b")
        public int diff(int a, int b) {
            System.out.println("[TOOL RUN] Subtraindo: " + a + " - " + b);
            return a - b;
        }

        @Tool(description = "divisão de dois números inteiros, a e b")
        public int div(int a, int b) {
            System.out.println("[TOOL RUN] Dividindo: " + a + " / " + b);
            return a / b;
        }
    }

    @Test
    void should_executeSum_whem_prompted() {
        var response = chatClient.prompt()
                .system("Você é um matemático e pesquisador doutorando")
                .user("Some 2 + 2; Subtraia 8 - 4; Divida 16 / 4; Multiplique 4 * 5.")
                .tools(new MathTools())
                .call()
                .content();

        assertThat(response).contains("4");
        System.out.println(response);
    }
}

