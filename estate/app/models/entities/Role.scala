package models.entities

import play.api.db.slick.Config.driver.simple._

case class Role(id: Option[Int], role: String)

object Roles extends Table[Role]("ROLES") {
    def id = column[Int]("ROLE_ID", O.PrimaryKey, O.AutoInc)
    def role = column[String]("ROLE")
    def * = id.? ~ role <>(Role, Role.unapply _)
    def roleIndex = index("role_idx", role, unique = true)
}