import models.dbconf.AppDB
import play.api.{Application, GlobalSettings}
import scala.slick.session.Session

/**
 *          Date: 21.09.13
 */
object Global extends GlobalSettings {

    override def onStart(app: Application) {
        implicit val application: Application = app

        lazy val database = AppDB.database
        lazy val dal = AppDB.dal

        database withSession { implicit session: Session =>
            dal.create
        }
    }

}
