package models.dbconf

import scala.slick.driver.ExtendedProfile
import models.DatabaseComponent

/**
 *          Date: 21.09.13
 */
trait Profile {
    val profile: ExtendedProfile
}

class DAL(override val profile: ExtendedProfile) extends DatabaseComponent with Profile {

    import profile.simple._

    def create(implicit session: Session): Unit = (Roles.ddl ++ Users.ddl).create

    def drop(implicit session: Session): Unit = (Roles.ddl ++ Users.ddl).drop

}

import play.api.Play.current

object AppDB extends DBeable {
    def database = getDb
    def dal = getDal
}