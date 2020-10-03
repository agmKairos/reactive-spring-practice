package es.kairosds.reactive.practice.blog.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "es.kairosds.reactive.practice.blog.domain.repository")
@Slf4j
public class MongoConfig extends AbstractReactiveMongoConfiguration {

	@Value("${spring.data.mongodb.uri}")
	private String mongoUri;

	@Autowired
	private MongoClient mongoClient;

	private ConnectionString connectionString;

	@PostConstruct
	public void init() {
		LOGGER.debug("Configurating Mongo with connection string: '{}'", mongoUri);
		this.connectionString = new ConnectionString(mongoUri);
	}

	@Override
	public MongoClient reactiveMongoClient() {
		return mongoClient;
	}

	@Override
	protected String getDatabaseName() {
		return connectionString.getDatabase();
	}
}
