package events.paiya.accountmanager.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("events.paiya.accountmanager.repositories")
@EnableMongoAuditing
public class MongoDbConfig {
}
