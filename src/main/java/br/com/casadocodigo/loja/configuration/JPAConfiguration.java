package br.com.casadocodigo.loja.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
public class JPAConfiguration {

	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
			Properties additionalProperties) {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPackagesToScan("br.com.casadocodigo.loja.models");
		factoryBean.setDataSource(dataSource);

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		factoryBean.setJpaProperties(additionalProperties);

		return factoryBean;
	}

	@Bean
	public Properties additionalProperties() {
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		return props;
	}

	@Bean
	@Profile("prod")
	public DataSource dataSourceProd() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername(System.getenv("USUARIO"));
		dataSource.setPassword(System.getenv("SENHA"));
		dataSource.setUrl(System.getenv("JDBC_CONNECTION_STRING"));
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		return dataSource;
	}


	@Bean
	@Profile("dev")
	public DataSource dataSourceDev() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername(System.getenv("USUARIO"));
		dataSource.setPassword(System.getenv("SENHA"));
		dataSource.setUrl(System.getenv("JDBC_CONNECTION_STRING"));
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		return dataSource;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

}
