package com.base.springboot.declarativetransaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.PROXY)
@Slf4j
public class DeclarativeTransactionDemoApplication implements CommandLineRunner {
	@Autowired
	private StudentService studentService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DeclarativeTransactionDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		studentService.insertRecord();
		log.info("AAA {}",
				jdbcTemplate
						.queryForObject("SELECT COUNT(*) FROM Student WHERE NAME='AAA'", Long.class));
		try {
			studentService.insertThenRollback();
		} catch (Exception e) {
			log.info("BBB {}",
					jdbcTemplate
							.queryForObject("SELECT COUNT(*) FROM Student WHERE NAME='BBB'", Long.class));
		}

		try {
			studentService.invokeInsertThenRollback1();
		} catch (Exception e) {
			log.info("BBB {}",
					jdbcTemplate
							.queryForObject("SELECT COUNT(*) FROM Student WHERE NAME='BBB'", Long.class));
		}

		try {
			studentService.invokeInsertThenRollback2();
		} catch (Exception e) {
			log.info("BBB {}",
					jdbcTemplate
							.queryForObject("SELECT COUNT(*) FROM Student WHERE NAME='BBB'", Long.class));
		}

		try {
			studentService.invokeInsertThenRollback3();
		} catch (Exception e) {
			log.info("BBB {}",
					jdbcTemplate
							.queryForObject("SELECT COUNT(*) FROM Student WHERE NAME='BBB'", Long.class));
		}
	}
}

