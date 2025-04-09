package org.balhom.transactionsapi.modules.transactions.infrastructure.persistence.sql

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.transactionsapi.modules.transactions.infrastructure.persistence.sql.data.TransactionSqlEntity

@ApplicationScoped
class TransactionSqlRepository : PanacheRepository<TransactionSqlEntity>
