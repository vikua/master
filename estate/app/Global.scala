import models.dbconf.AppDB
import play.api.{Application, GlobalSettings}
import scala.slick.session.Session
import app.inject._
/**
 * Date: 21.09.13
 */
object Global extends GlobalSettings {

    import AppDB._

    override def onStart(app: Application) {
        implicit val application: Application = app

        database withSession {
            implicit session: Session =>
                dal.create
        }
    }

    override def onStop(app: Application) {
        implicit val application: Application = app

        database withSession {
            implicit session: Session =>
                dal.drop
        }
    }

    override def getControllerInstance[A](controllerClass: Class[A]): A = injector.getInstance(controllerClass)
}
