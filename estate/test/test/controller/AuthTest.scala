package test.controller

import play.api.test._
import play.api.test.Helpers._
import models.dbconf.AppDB._
import controllers.Authentication
import org.scalatest.{ShouldMatchers, FlatSpec}
import play.api.db.slick.Config.driver.simple._
import models.entities.{Users, User}

/**
 * Date: 24.09.13
 */
class AuthTest extends FlatSpec with ShouldMatchers {

    "User" should "pass authentication" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
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
            val exists = Authentication.check("test@test.com", "123")
            exists shouldEqual (false)
        }
    }

}
