package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
@RunWith(classOf[JUnitRunner])
class IntegrationSpec extends Specification {

    "Application" should {

        "work from within a browser" in {
            running(TestServer(3333), HTMLUNIT) {
                browser =>
                    browser.goTo("http://localhost:3333/")
                    browser.pageSource must contain("Sign in")

            }
        }

    }

}