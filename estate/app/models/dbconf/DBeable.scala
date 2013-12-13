package models.dbconf

import play.api.Application
import scala.slick.driver.ExtendedProfile
import scala.slick.session.Database
import play.api.db.DB

/**
 *          Date: 22.09.13
 */
trait DBeable {

    def getDb(implicit app: Application) = Database.forDataSource(DB.getDataSource())

}

import play.api.Play.current

object AppDB extends DBeable {
    def database = getDb
}


