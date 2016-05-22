package com.gv.neo4j.configuration;



import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories("com.gv.neo4j.repository")
@EnableTransactionManagement
@ComponentScan("com.gv.neo4j")
public class PersistenceContext extends Neo4jConfiguration{

	@Override
	public Neo4jServer neo4jServer() {
		return new RemoteServer("http://localhost:7474");
	}

	@Override
	public SessionFactory getSessionFactory() {
		
		return null;
	}

}
