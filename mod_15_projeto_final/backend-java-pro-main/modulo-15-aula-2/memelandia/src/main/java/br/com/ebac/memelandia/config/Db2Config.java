package br.com.ebac.memelandia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/**
 * Configuração do banco de dados NoSQL (MongoDB) para o pacote repositories.db2.
 *
 * Diferente do JPA/MySQL (Db2Config), o MongoDB não usa DataSource JDBC,
 * EntityManagerFactory nem Hibernate. Ele usa MongoClient + MongoDatabaseFactory + MongoTemplate.
 */
@Configuration
@EnableMongoRepositories(
        basePackages = "br.com.ebac.memelandia.repositories.db2",
        mongoTemplateRef = "mongoTemplate"
)
public class Db2Config {

    @Value("${spring.db2.datasource.url}")
    private String mongoUrl;

    @Value("${spring.db2.datasource.username}")
    private String username;

    @Value("${spring.db2.datasource.password}")
    private String password;

    /**
     * Cria o cliente de conexão nativo do MongoDB com credenciais autenticando no banco "memelandia".
     */
    @Bean(name = "mongoClient")
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoUrl);
        MongoCredential credential = MongoCredential.createCredential(
                username,
                "admin",
                password.toCharArray()
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .credential(credential)
                .build();

        return MongoClients.create(settings);
    }

    /**
     * Cria a fábrica de conexões apontando para o database definido na URL (ex.: "memelandia").
     */
    @Bean(name = "mongoDatabaseFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(MongoClient mongoClient) {
        String database = new ConnectionString(mongoUrl).getDatabase();
        return new SimpleMongoClientDatabaseFactory(
                mongoClient,
                database != null ? database : "memelandia"
        );
    }

    /**
     * Template principal utilizado pelos repositórios Spring Data MongoDB (MongoRepository).
     */
    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
