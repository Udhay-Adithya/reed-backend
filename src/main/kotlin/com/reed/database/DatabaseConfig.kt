package com.reed.database

import com.reed.config.AppConfig
import com.reed.database.Artists
import com.reed.database.Songs
import com.reed.database.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseConfig {
    fun init() {
        val config = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl =
                "jdbc:postgresql://${AppConfig.DB_HOST}:${AppConfig.DB_PORT}/${AppConfig.DB_NAME}"
            username = AppConfig.DB_USER
            password = AppConfig.DB_PASSWORD
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"

            // PostgreSQL specific settings
            addDataSourceProperty("cachePrepStmts", "true")
            addDataSourceProperty("prepStmtCacheSize", "250")
            addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        }

        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)

        transaction {
            // Create all tables
            SchemaUtils.createMissingTablesAndColumns(
                Users,
                Artists,
                Songs
            )
        }
    }
}
