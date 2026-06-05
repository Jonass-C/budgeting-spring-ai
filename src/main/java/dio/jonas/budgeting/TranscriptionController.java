package dio.jonas.budgeting;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class TranscriptionController {
    private final ChatClient chatClient;

    public TranscriptionController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping(value = "/transcribe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String transcribe(@RequestParam("file") MultipartFile file) {
        return this.chatClient.prompt()
                .system("Você é um assistente financeiro. Transcreva o áudio em anexo. Retorne estritamente o texto dito pelo usuário, sem saudações ou explicações.")
                .user(promptUserSpec -> promptUserSpec
                        .text("Áudio em anexo:")
                        .media(MimeTypeUtils.parseMimeType(file.getContentType()), file.getResource()))
                .call()
                .content();
    }
}
