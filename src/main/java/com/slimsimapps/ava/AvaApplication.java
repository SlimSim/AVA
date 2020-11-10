package com.slimsimapps.ava;

import com.slimsimapps.ava.service.BadLogService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AvaApplication extends SpringBootServletInitializer {




	// Makes deployment to war possible:
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AvaApplication.class);
	}

	public static void main(String[] args) {
		BadLogService.start();
		SpringApplication.run(AvaApplication.class, args);
		BadLogService.end();
	}

	/*
	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			// save a few customers
			repository.save(new Customer("Jack", "Bauer"));
			repository.save(new Customer("Chloe", "O'Brian"));
			repository.save(new Customer("Kim", "Bauer"));
			repository.save(new Customer("David", "Palmer"));
			repository.save(new Customer("Michelle", "Dessler"));

			// fetch all customers
			log.i("Customers found with findAll():");
			log.i("-------------------------------");
			for (Customer customer : repository.findAll()) {
				log.i(customer.toString());
			}
			log.i("");

			// fetch an individual customer by ID
			Customer customer = repository.findById(1L);
			log.i("Customer found with findById(1L):");
			log.i("--------------------------------");
			log.i(customer.toString());
			log.i("");

			// fetch customers by last name
			log.i("Customer found with findByLastName('Bauer'):");
			log.i("--------------------------------------------");
			repository.findByLastName("Bauer").forEach(bauer -> {
				log.i(bauer.toString());
			});
			// for (Customer bauer : repository.findByLastName("Bauer")) {
			//  log.i(bauer.toString());
			// }
			log.i("");
		};
	}
	 */
}
