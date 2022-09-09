package com.example.nplusone;

import com.example.nplusone.repository.TutorialRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootJpaH2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaH2Application.class, args);
	}

	@Bean
	CommandLineRunner runner(TutorialRepository repository) {
		return args -> {
//			Tutorial entity = new Tutorial();
//			entity.setDescription("Desc");
//			entity.setTitle("Title");
//			Tutorial saved = repository.save(entity);
//			repository.findById(saved.getId());
//			System.out.println("--------------------------------------");
//			Tutorial referenceById = repository.getReferenceById(saved.getId());
//			Thread.sleep(2_000);
//			System.out.println(referenceById.getId());
//
//			entityManager.find(Tutorial.class, saved.getId());
//			System.out.println("--------------------------------------");
//			entityManager.getReference(Tutorial.class, saved.getId());
//			System.out.println("--------------------------------------");
		};
	}

}
