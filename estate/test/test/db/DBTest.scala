package test.db

import models.{User, Role}
import play.api.test._
import play.api.test.Helpers._
import models.dbconf.AppDB._
import scala.slick.session.Session
import org.scalatest.{ShouldMatchers, FlatSpec}

/**
 * Date: 22.09.13
 */
class DBTest extends FlatSpec with ShouldMatchers {

    "Roles" should "be saved" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
            val d = dal

            import d._
            import d.profile.simple._

            database withSession {
                implicit session: Session =>
                    Roles.insertAll(
                        Role(None, "ADMIN"),
                        Role(None, "USER"),
                        Role(None, "EXPERT")
                    )

                    val list = Query(Roles).list()
                    list.size should be (3)
                    list.forall(_.id.isDefined) should be (true)
            }
        }
    }

    it should "be deleted" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
            val d = dal

            import d._
            import d.profile.simple._

            database withSession {
                implicit session: Session =>
                    Roles.insertAll(
                        Role(None, "ADMIN"),
                        Role(None, "USER"),
                        Role(None, "EXPERT")
                    )
                    val list = Query(Roles).list()
                    list.size should be (3)

                    Query(Roles).filter(r => r.id === 1).delete
                    val remainingList = Query(Roles).list
                    remainingList.size should be (2)
            }
        }
    }

    "Users" should "be saved" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
            val d = dal

            import d._
            import d.profile.simple._

            database withSession {
                implicit session: Session =>
                    Users.insertAll(
                        User(None, "test1@gmail.com", "123", "test1", "test1", None),
                        User(None, "test2@gmail.com", "123", "test2", "test2", None),
                        User(None, "test3@gmail.com", "123", "test3", "test3", None)
                    )
                    val users = Query(Users).list()
                    users.isEmpty should be (false)
                    users.size should be (3)
                    users.forall(_.id.isDefined) should be (true)
                    users.forall(_.password === "123") should be (true)
            }
        }
    }
    it should "be saved and attached to Roles" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
            val d = dal

            import d._
            import d.profile.simple._

            database withSession {
                implicit session: Session =>
                    Roles.insertAll(
                        Role(Some(1), "USER"),
                        Role(Some(2), "EXPERT")
                    )
                    Users.insertAll(
                        User(None, "test1@gmail.com", "123", "test1", "test1", Some(1)),
                        User(None, "test2@gmail.com", "123", "test2", "test2", Some(2)),
                        User(None, "test3@gmail.com", "123", "test3", "test3", Some(2))
                    )
                    val joinQuery = for {
                        (u, r) <- Users innerJoin Roles on (_.roleId === _.id)
                    } yield (u, r)
                    val list = joinQuery.list()
                    list.count(u => u._2.id == Some(1)) should be (1)
                    list.count(u => u._2.id == Some(2)) should be (2)
            }
        }
    }

}
