package test.integration

import play.api.test._
import play.api.test.Helpers._
import org.scalatest.{ShouldMatchers, FlatSpec}

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends FlatSpec with ShouldMatchers {

    "Application" should "send 404 on a bad request" in {
        running(FakeApplication()) {
            route(FakeRequest(GET, "/boum")) shouldBe None
        }
    }

    it should "render the index page" in {
        running(FakeApplication()) {
            val home = route(FakeRequest(GET, "/")).get

            status(home) shouldEqual SEE_OTHER
            contentType(home) shouldBe None
        }
    }
}