package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import slick.driver.JdbcProfile

import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.libs.json.Json._

import scala.concurrent.Future

case class FavouriteService(userId: Int, serviceId: Int)

class FavouriteServiceRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.driver.api._

  private val FavouriteServices = TableQuery[FavouriteServicesTable]

  private def _findAllUsers: Query[FavouriteServicesTable, FavouriteService, List] =
    FavouriteServices.distinctOn(_.userId).to[List]

  private def _findAllByUserId(userId:Int): Query[FavouriteServicesTable, FavouriteService, List] =
    FavouriteServices.filter(_.userId === userId).to[List]

  private def _findAllByUserIdAndServiceId(userId:Int, serviceId:Int): Query[FavouriteServicesTable, FavouriteService, List] =
    _findAllByUserId(userId).filter(_.serviceId === serviceId) // complicated filter?? is it correct way?

  def add(favouriteService: FavouriteService): Future[Int] = {
    db.run(FavouriteServices += favouriteService)
  }

  def findAll: Future[List[FavouriteService]] = {
    db.run(_findAllUsers.result)
  }
  def findAllByUserId(userId:Int): Future[List[FavouriteService]] = {
    db.run(_findAllByUserId(userId).result)
  }

  def findAllByUserIdAndServiceId(userId:Int, serviceId:Int): Future[List[FavouriteService]] = {
    db.run(_findAllByUserIdAndServiceId(userId,serviceId).result)
  }

  def delete(userId:Int, serviceId:Int): Future[Int] = {
    val query = _findAllByUserIdAndServiceId(userId,serviceId)
    val interaction = query.delete

    db.run(interaction.transactionally)
  }

  private class FavouriteServicesTable(tag: Tag) extends Table[FavouriteService](tag, "FavouriteService") {

    def userId = column[Int]("userId")
    def serviceId = column[Int]("serviceId")

    def * = (userId, serviceId) <> (FavouriteService.tupled, FavouriteService.unapply)

  }
}