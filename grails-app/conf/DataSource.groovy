dataSource {
	pooled = true
	driverClassName = "org.hsqldb.jdbcDriver"
	username = "sa"
	password = ""
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
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
			driverClassName = "com.mysql.jdbc.Driver"
			logSql = false
			dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'
			url = "jdbc:mysql://localhost:3306/goblin_dev"
			username = 'goblin'
			password = 'goblin'
		}
		
	}
	test {
		dataSource {
			dbCreate = "update"
			url = "jdbc:hsqldb:mem:testDb"
		}
	}
	production {
		dataSource {
			dbCreate = "create"
//			url = "jdbc:hsqldb:mem:devDB"
//			url = "jdbc:hsqldb:file:prodDb;shutdown=true"
			pooling = true
            logSql = false
            // enable the following lines in exchange to the mysql-related ones
            // to use the postgresql database.
//            driverClassName = "org.postgresql.Driver"
//            dialect = "org.hibernate.dialect.PostgreSQLDialect"
//            url = "jdbc:postgresql://127.0.0.1:5432/goblin"
            driverClassName = "com.mysql.jdbc.Driver"
			dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'
			url = "jdbc:mysql://localhost:3306/goblin"
			username = 'goblin'
			password = 'goblin'
            autoReconnect = true		
		}
	}
}