package test.controller

import play.api.test._
import play.api.test.Helpers._
import models.dbconf.AppDB._
import models.User
import controllers.Authentication
import org.scalatest.{ShouldMatchers, FlatSpec}

/**
 * Date: 24.09.13
 */
class AuthTest extends FlatSpec with ShouldMatchers {

    "User" should "pass authentication" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
            val data = dal
            import data._
            import data.profile.simple._

            val email = "test@test.com"
            val pass = "test"

            database withSession {
                implicit session: Session =>
                    Users.insert(User(None, email, pass, "test", "test", None))
            }

            val exists = Authentication.check(email, pass)
            exists shouldEqual (true)
        }
    }

    it should "fail authentication" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
            val data = dal

            val exists = Authentication.check("test@test.com", "123")
            exists shouldEqual (false)
        }
    }

}
