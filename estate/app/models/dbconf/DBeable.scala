package models.dbconf

import play.api.Application
import scala.slick.driver.ExtendedProfile
import scala.slick.session.Database
import play.api.db.DB

/**
 *          Date: 22.09.13
 */
trait DBeable {

    val SLICK_DRIVER = "slick.db.driver"
    val DEFAULT_SLICK_DRIVER = "scala.slick.driver.H2Driver"

    def getDal(implicit app: Application): DAL = {
        val driverClass = app.configuration.getString(SLICK_DRIVER).getOrElse(DEFAULT_SLICK_DRIVER)
        val driver = singleton[ExtendedProfile](driverClass)
        new DAL(driver)
    }

    def getDb(implicit app: Application) = Database.forDataSource(DB.getDataSource())

    private def singleton[T](name: String)(implicit manifest: Manifest[T]): T =
        Class.forName(name + '$').getField("MODULE$").get(manifest.runtimeClass).asInstanceOf[T]

}

import play.api.Play.current

object AppDB extends DBeable {
    def database = getDb
    def dal = getDal
}


