package controllers

import play.api.mvc._
import models.dbconf.AppDB._
import play.api.db.slick.Config.driver.simple._
import models.entities.{Users, User}

/**
 * Date: 24.09.13
 */
trait Secured {

    def username(request: RequestHeader) = request.session.get(Security.username)

    def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Authentication.login)

    def isAuthenticated(f: => String => Request[AnyContent] => Result) = Security.Authenticated(username, onUnauthorized) {
        user =>
            Action(request => f(user)(request))
    }

    def withUser(f: => User => Request[AnyContent] => Result) = isAuthenticated {
        user => request =>
            val userOpt: Option[User] = database withSession {
                implicit session: scala.slick.session.Session =>
                    Users.map {
                        e => e
                    }.where(u => u.email === user).take(1).firstOption
            }
            userOpt match {
                case None => Results.Forbidden
                case Some(i: User) => f(i)(request)
            }
    }
}
