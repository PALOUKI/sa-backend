package tech.root.hsa.sa;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.root.hsa.sa.repository.SentimentRepository;
import tech.root.hsa.sa.service.SentimentService;

@AllArgsConstructor
@SpringBootApplication
public class SaBackendApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SaBackendApplication.class, args);
	}

	private SentimentService sentimentService;
	@Override
	public void run(String... args) throws Exception {
		 sentimentService.initializeSentiments();
	}
}
