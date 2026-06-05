package dio.jonas.budgeting;

import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "GEMINI_API_KEY", matches = ".+")
public class GeminiTranscriptionIT {

    @Autowired
    ChatClient chatClient;

    @ParameterizedTest
    @CsvSource({"recording.mp3, compras"})
    public void should_containExpectedKeywords_when_audioFilesAreProcessed(String fileName, String expectedKeyword) {
        var recording = new ClassPathResource("audio/" + fileName);

        var response = chatClient.prompt()
                .system("Você é um assistente financeiro. Transcreva o áudio em anexo. Retorne estritamente o texto dito pelo usuário, sem saudações ou explicações.")
                .user(promptUserSpec -> promptUserSpec
                        .text("Áudio em anexo:")
                        .media(MimeTypeUtils.parseMimeType("audio/mpeg"), recording))
                .call()
                .content();

        assertThat(response.toLowerCase()).contains(expectedKeyword);
        System.out.println("Transcrição: " + response);
    }
}
