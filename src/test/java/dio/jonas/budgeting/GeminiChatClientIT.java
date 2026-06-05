package dio.jonas.budgeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "GEMINI_API_KEY", matches = ".+")
public class GeminiChatClientIT {

    @Autowired
    private ChatClient chatClient;

    @Test
    void should_executeSum_whem_prompted() {
        var response = chatClient.prompt()
                .system("Você é um matemático e pesquisador doutorando")
                .user("Em apenas uma linha: Informe qual o valor da derivada de x e depois subtraia pelo valor da derivada parcial de y em (x+y)")
                .call()
                .content();

        assertThat(response).contains("0");
        System.out.println(response);
    }
}
