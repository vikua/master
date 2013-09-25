package app.inject

import com.google.inject.{Guice, Provides, AbstractModule}
import models.dbconf.DAL
import scala.slick.session.Database

/**
 *          Date: 25.09.13
 */
class EstateGuiceModule extends AbstractModule {

    override def configure(): Unit = {
//        bind(classOf[TestBean]) to classOf[ConcreteTestBean]
    }

    @Provides() def provideDatabaseAccess(): DAL = models.dbconf.AppDB.dal
    @Provides() def provideDatabase(): Database = models.dbconf.AppDB.database
}
