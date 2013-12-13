package models.entities

import play.api.db.slick.Config.driver.simple._
import scala.slick.lifted.ColumnBase

case class User(id: Option[Int], email: String, password: String, firstName: String, lastName: String, roleId: Option[Int])

object Users extends Table[User]("USERS") {
    def id = column[Int]("USER_ID", O.PrimaryKey, O.AutoInc)
    def email = column[String]("EMAIL", O.NotNull)
    def password = column[String]("PASSWORD", O.NotNull)
    def firstName = column[String]("FIRST_NAME")
    def lastName = column[String]("LAST_NAME")
    def roleId = column[Int]("ROLE_ID", O.Nullable)
    def role = foreignKey("ROLE_FK", roleId, Roles)(_.id)
    def * : ColumnBase[User] = id.? ~ email ~ password ~ firstName ~ lastName ~ roleId.? <>(User, User unapply _)
    def emailIndex = index("email_idx", email, unique = true)
}