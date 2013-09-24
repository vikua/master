package controllers

import play.api.mvc._
import models.User
import models.dbconf.AppDB._

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
            val data = dal
            import data._
            import data.profile.simple._

            val userOpt: Option[User] = database withSession {
                implicit session: Session =>
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
