package controller

import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._
import models.dbconf.AppDB._
import models.User
import controllers.Authentication
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import play.GlobalSettings

/**
 *          Date: 24.09.13
 */
@RunWith(classOf[JUnitRunner])
class AuthTest extends Specification {

    "User" should {
        "pass authentication" in {
            running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
                val data = dal
                import data._
                import data.profile.simple._

                val email = "test@test.com"
                val pass = "test"

                database withSession { implicit session: Session =>
                    Users.insert(User(None, email, pass, "test", "test", None))
                }

                val exists = new Authentication(data).check(email, pass)
                exists must beTrue
            }
        }

        "fail authentication" in {
            running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
                val data = dal
                import data._
                import data.profile.simple._

                val exists = new Authentication(data).check("test@test.com", "123")
                exists must beFalse
            }
        }
    }

}
