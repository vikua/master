package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._
/**
 *          Date: 9/29/13
 */
object Register extends Controller {

    val registrationForm = Form(
        mapping(
            "email" -> email,
            "password" -> nonEmptyText,
            "firstName" -> nonEmptyText,
            "lastName" -> nonEmptyText
        )((email, password, firstName, lastName) => User(None, email, password, firstName, lastName, None))
         ((user: User) => Some(user.email, user.password, user.firstName, user.lastName))
    )

    def registration = Action {
        Ok(views.html.register(registrationForm))
    }

    def register = Action { implicit request =>
        val body = registrationForm.bindFromRequest
        Ok
    }


}
