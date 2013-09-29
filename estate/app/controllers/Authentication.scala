package controllers

import play.api.mvc.{Action, Controller}
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api._
import scala.slick.session.Session
import models.dbconf.AppDB._
import models.User
import javax.inject.{Inject, Singleton}
import models.dbconf.DAL
import scala.beans._
import scala.annotation.meta.beanSetter

/**
 *          Date: 22.09.13
 */
@Singleton
class Authentication @Inject() (val dal: DAL) extends Controller {

    val loginForm = Form(
        tuple(
            "email" -> nonEmptyText,
            "password" -> nonEmptyText
        ) verifying ("Invalid login or password", result => result match {
            case (email, password) => check(email, password)
        })
    )

    def check(email: String, password: String) = {
        import dal.profile.simple._
        import dal._

        val user = database withSession { implicit session: Session =>
            Users.map {e => e}.where(u => u.email === email && u.password === password).take(1).firstOption
        }
        user.isDefined
    }

    def login = Action { implicit request =>
        Ok(views.html.login(loginForm))
    }

    def authenticate = Action { implicit request =>
        loginForm.bindFromRequest.fold(
            formWithErrors => BadRequest(views.html.login(formWithErrors)),
            user => Redirect(routes.Application.index()).withSession("email" -> user._1)
        )
    }

    def logout = Action {
        Redirect(routes.Authentication.login()).withNewSession.flashing(
            "success" -> "You've been logged out"
        )
    }

}
