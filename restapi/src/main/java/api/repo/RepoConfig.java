package api.repo;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "api/repo")
@EntityScan(basePackages = {"api/repo"})
@ComponentScan("api/repo")
public class RepoConfig {

}
