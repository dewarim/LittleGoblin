dataSource {
	pooled = true
}
hibernate {
    cache.use_second_level_cache=false
    cache.use_query_cache=false
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
//    connection.isolation=4
}
// environment specific settings
environments {
	development {
//		dataSource {
//			dbCreate = "update" // one of 'create', 'create-drop','update'
//			url = "jdbc:hsqldb:mem:devDB"
//		}
		dataSource {
			dbCreate = "create"
			pooling = true
            logSql = false
//            driverClassName = "com.mysql.jdbc.Driver"
//			dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'
//			url = "jdbc:mysql://localhost:3306/goblin_dev"

            driverClassName = "org.postgresql.Driver"
            dialect = "org.hibernate.dialect.PostgreSQLDialect"
            url = "jdbc:postgresql://127.0.0.1:5432/goblin"
            
			username = 'goblin'
			password = 'goblin'
		}
		
	}
	test {
		dataSource {
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
            dbCreate = "update"
            url = "jdbc:h2:mem:goblin"
		}
	}
	production {
		dataSource {
			dbCreate = "update"
//			url = "jdbc:hsqldb:mem:devDB"
//			url = "jdbc:hsqldb:file:prodDb;shutdown=true"
			pooling = true
            logSql = false
            // enable the following lines in exchange to the mysql-related ones
            // to use the postgresql database.
            driverClassName = "org.postgresql.Driver"
            dialect = "org.hibernate.dialect.PostgreSQLDialect"
            url = "jdbc:postgresql://127.0.0.1:5432/goblin"
//            driverClassName = "com.mysql.jdbc.Driver"
//			dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'
//			url = "jdbc:mysql://localhost:3306/goblin"
			username = 'goblin'
			password = 'goblin'
            autoReconnect = true
            properties {
                maxActive = -1
                minEvictableIdleTimeMillis=1800000
                timeBetweenEvictionRunsMillis=1800000
                numTestsPerEvictionRun=3
                testOnBorrow=true
                testWhileIdle=true
                testOnReturn=true
                validationQuery="SELECT 1"
            }
		}
	}
}
