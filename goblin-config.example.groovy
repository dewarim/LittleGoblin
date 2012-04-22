// ID of this application (used by remote processes to trigger events)
goblinId = '12345'
gameName = 'Little Goblin'
serverUrl = 'http://schedim.de'

environments {
	development {
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

	production {
		dataSource {
            // set dbCreate to "update" or "none" to make the database persistent.
			dbCreate = "create"
			pooling = true
            logSql = false

            // enable the following lines in exchange to the mysql-related ones
            // to use the postgresql database.
            // PostgreSQL:
            driverClassName = "org.postgresql.Driver"
            dialect = "org.hibernate.dialect.PostgreSQLDialect"
            url = "jdbc:postgresql://127.0.0.1:5432/goblin"
            // MySQL:
//            driverClassName = "com.mysql.jdbc.Driver"
//			dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'
//			url = "jdbc:mysql://localhost:3306/goblin"

            // Username + Password:
			username = 'goblin'
			password = 'goblin'

            autoReconnect = true
		}
	}
}

facebook {
    enabled=true
    enableLikeButton = true
    enableFacebookLogin = false
    myUrl = 'http://schedim.de/'
    appId = '217240194988212'
}