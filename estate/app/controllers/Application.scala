package controllers

import play.api._
import play.api.mvc._

object Application extends Controller with Secured{
  
  def index = isAuthenticated { username => request =>
    Ok(views.html.index("Your new application is ready."))
  }
  
}