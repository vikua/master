package test.integration

import play.api.test._
import play.api.test.Helpers._
import org.scalatest.{ShouldMatchers, FlatSpec}

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
class IntegrationSpec extends FlatSpec with ShouldMatchers {

    "Application" should "work from within a browser" in {
        running(TestServer(3333), HTMLUNIT) {
            browser =>
                browser.goTo("http://localhost:3333/")
                browser.pageSource should include("Sign in")

        }
    }

}