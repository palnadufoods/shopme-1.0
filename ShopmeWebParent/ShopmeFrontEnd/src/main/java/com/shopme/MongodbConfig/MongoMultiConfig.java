package com.shopme.MongodbConfig;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.shopme.ProductMgoRepos.repo0", mongoTemplateRef = "mongoTemplate")
//@EnableConfigurationProperties(MongoMultiProperties.class)
public class MongoMultiConfig {

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient, MongoProperties mongoProperties) {
    	mongoProperties.setDatabase("shopme");
        return new MongoTemplate(mongoClient, mongoProperties.getDatabase());
    }

    @Bean
    public MongoClient mongoClient(MongoProperties mongoProperties) {
		/*
		 * ConnectionString connectionString = new ConnectionString("mongodb://" +
		 * mongoProperties.getHost() + ":" + mongoProperties.getPort() + "/" +
		 * mongoProperties.getDatabase());
		 */
        
        ConnectionString conString=new ConnectionString("mongodb+srv://saibadineedi:Fassak%4008@cluster0.jgly6aj.mongodb.net/");        
        return MongoClients.create(conString);
    }
}

@Configuration
@EnableMongoRepositories(basePackages = "com.shopme.ProductMgoRepos.repo1", mongoTemplateRef = "mongoTemplate1")
//@EnableConfigurationProperties(MongoMultiProperties.class)
class MongoMultiConfig1 {

    @Bean
    public MongoTemplate mongoTemplate1(MongoClient mongoClient1, MongoProperties mongoProperties) {
    	mongoProperties.setDatabase("shopme");
        return new MongoTemplate(mongoClient1, mongoProperties.getDatabase());
    }

    @Bean
    public MongoClient mongoClient1(MongoProperties mongoProperties) {
		
        ConnectionString conString=new ConnectionString("mongodb+srv://saibadineedi:Fassak%4008@cluster1.zdhyokd.mongodb.net/");        
        return MongoClients.create(conString);
    }
    
}



@Configuration
@EnableMongoRepositories(basePackages = "com.shopme.ProductMgoRepos.repo2", mongoTemplateRef = "mongoTemplate2")
//@EnableConfigurationProperties(MongoMultiProperties.class)
class MongoMultiConfig2 {

    @Bean
    public MongoTemplate mongoTemplate2(MongoClient mongoClient2, MongoProperties mongoProperties) {
    	mongoProperties.setDatabase("shopme");
        return new MongoTemplate(mongoClient2, mongoProperties.getDatabase());
    }

    @Bean
    public MongoClient mongoClient2(MongoProperties mongoProperties) {
		
        ConnectionString conString=new ConnectionString("mongodb+srv://pranavibadineedi:D59RaEEqcuoCTfJS@cluster2.ilb1vh1.mongodb.net/");        
        return MongoClients.create(conString);
    }
    
}



@Configuration
@EnableMongoRepositories(basePackages = "com.shopme.ProductMgoRepos.repo3", mongoTemplateRef = "mongoTemplate3")
class MongoMultiConfig3 {

    @Bean
    public MongoTemplate mongoTemplate3(MongoClient mongoClient3, MongoProperties mongoProperties) {
        mongoProperties.setDatabase("shopme");
        return new MongoTemplate(mongoClient3, mongoProperties.getDatabase());
    }

    @Bean
    public MongoClient mongoClient3(MongoProperties mongoProperties) {
        ConnectionString conString = new ConnectionString("mongodb+srv://chsureshreddy321:I4BQRq5ZllKQVZKa@cluster3.6joummr.mongodb.net/");
        return MongoClients.create(conString);
    }
}

@Configuration
@EnableMongoRepositories(basePackages = "com.shopme.ProductMgoRepos.repo4", mongoTemplateRef = "mongoTemplate4")
//@EnableConfigurationProperties(MongoMultiProperties.class)
class MongoMultiConfig4 {

    @Bean
    public MongoTemplate mongoTemplate4(MongoClient mongoClient4, MongoProperties mongoProperties) {
        mongoProperties.setDatabase("shopme");
        return new MongoTemplate(mongoClient4, mongoProperties.getDatabase());
    }

    @Bean
    public MongoClient mongoClient4(MongoProperties mongoProperties) {
        ConnectionString conString = new ConnectionString("mongodb+srv://nagalakshmibadineedi466:wVQrOvqIdIlyKMAU@cluster4.gmne8yu.mongodb.net/");
        return MongoClients.create(conString);
    }
}

@Configuration
@EnableMongoRepositories(basePackages = "com.shopme.ProductMgoRepos.repo5", mongoTemplateRef = "mongoTemplate5")
//@EnableConfigurationProperties(MongoMultiProperties.class)
class MongoMultiConfig5 {

    @Bean
    public MongoTemplate mongoTemplate5(MongoClient mongoClient5, MongoProperties mongoProperties) {
        mongoProperties.setDatabase("shopme");
        return new MongoTemplate(mongoClient5, mongoProperties.getDatabase());
    }

    @Bean
    public MongoClient mongoClient5(MongoProperties mongoProperties) {
        ConnectionString conString = new ConnectionString("mongodb+srv://221710307008:hsFFu2dm7Sq3nqAJ@cluster5.hejyeom.mongodb.net/");
        return MongoClients.create(conString);
    }
}

@Configuration
@EnableMongoRepositories(basePackages = "com.shopme.ProductMgoRepos.repo6", mongoTemplateRef = "mongoTemplate6")
//@EnableConfigurationProperties(MongoMultiProperties.class)
class MongoMultiConfig6 {

    @Bean
    public MongoTemplate mongoTemplate6(MongoClient mongoClient6, MongoProperties mongoProperties) {
        mongoProperties.setDatabase("shopme");
        return new MongoTemplate(mongoClient6, mongoProperties.getDatabase());
    }

    @Bean
    public MongoClient mongoClient6(MongoProperties mongoProperties) {
        ConnectionString conString = new ConnectionString("mongodb+srv://saradabadineedi1986:hlIbvfQjMRRuBA1F@cluster6.4hidd8c.mongodb.net/");
        return MongoClients.create(conString);
    }
}

@Configuration
@EnableMongoRepositories(basePackages = "com.shopme.ProductMgoRepos.repo7", mongoTemplateRef = "mongoTemplate7")
//@EnableConfigurationProperties(MongoMultiProperties.class)
class MongoMultiConfig7 {

    @Bean
    public MongoTemplate mongoTemplate7(MongoClient mongoClient7, MongoProperties mongoProperties) {
        mongoProperties.setDatabase("shopme");
        return new MongoTemplate(mongoClient7, mongoProperties.getDatabase());
    }

    @Bean
    public MongoClient mongoClient7(MongoProperties mongoProperties) {
        ConnectionString conString = new ConnectionString("mongodb+srv://babubadineedi:vyxHA7OCTUr9r9lj@cluster7.mndct6f.mongodb.net/");
        return MongoClients.create(conString);
    }
}

@Configuration
@EnableMongoRepositories(basePackages = "com.shopme.ProductMgoRepos.repo8", mongoTemplateRef = "mongoTemplate8")

class MongoMultiConfig8 {

    @Bean
    public MongoTemplate mongoTemplate8(MongoClient mongoClient8, MongoProperties mongoProperties) {
        mongoProperties.setDatabase("shopme");
        return new MongoTemplate(mongoClient8, mongoProperties.getDatabase());
    }

    @Bean
    public MongoClient mongoClient8(MongoProperties mongoProperties) {
        ConnectionString conString = new ConnectionString("mongodb+srv://badineedij:vFDJL5LXjY5WF3Nv@cluster8.4bkllnx.mongodb.net/");
        return MongoClients.create(conString);
    }
}


@Configuration
@EnableMongoRepositories(basePackages = "com.shopme.ProductMgoRepos.repo9", mongoTemplateRef = "mongoTemplate9")

class MongoMultiConfig9 {

    @Bean
    public MongoTemplate mongoTemplate9(MongoClient mongoClient9, MongoProperties mongoProperties) {
        mongoProperties.setDatabase("shopme");
        return new MongoTemplate(mongoClient9, mongoProperties.getDatabase());
    }

    @Bean
    public MongoClient mongoClient9(MongoProperties mongoProperties) {
        ConnectionString conString = new ConnectionString("mongodb+srv://jhansibadineedi:WLmVIJOdnue7TkVV@cluster9.spggyfg.mongodb.net/");
        return MongoClients.create(conString);
    }
}


