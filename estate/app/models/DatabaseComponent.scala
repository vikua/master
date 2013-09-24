package models

import models.dbconf.Profile
import scala.slick.lifted.ColumnBase

/**
 *          Date: 21.09.13
 */

case class Role(id: Option[Int], role: String)
case class User(id: Option[Int], email: String, password: String, firstName: String, lastName: String, roleId: Option[Int])

trait DatabaseComponent { this: Profile =>

    import profile.simple._

    object Roles extends Table[Role]("ROLES") {
        def id = column[Int]("ROLE_ID", O.PrimaryKey, O.AutoInc)
        def role = column[String]("ROLE")
        def * = id.? ~ role <>(Role, Role.unapply _)
        def roleIndex = index("role_idx", role, unique = true)
    }

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

}
