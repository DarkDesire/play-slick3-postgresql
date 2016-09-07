package controllers

import javax.inject.Inject

import models.{FavouriteService, FavouriteServiceRepo}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}

class Application @Inject()(favouriteServiceRepo: FavouriteServiceRepo) extends Controller {

  def index = Action.async { implicit rs =>
    favouriteServiceRepo.findAll
      .map(service => Ok(views.html.index( service.groupBy(_.userId).map(_._2.head).toList)))
    // as for me it's lifehack, because filtering after received all data
    // only for demo purpose, don't do that
  }
  //   PUT     /users/:userId/favouriteServices/:serviceId
  def add(userId:Int, serviceId:Int) = Action { implicit rs =>
    favouriteServiceRepo.add(FavouriteService(userId,serviceId))

    Redirect(controllers.routes.Application.index())
  }

  // GET     /users/:userId/favouriteServices
  def findAll(userId:Int) = Action.async { implicit rs =>
    favouriteServiceRepo.findAllByUserId(userId)
      .map(services => Ok(views.html.user(services)))
  }

  // GET     /users/:userId/favouriteServices/:serviceId
  def find(userId:Int,serviceId:Int) = Action.async { implicit rs =>
    favouriteServiceRepo.findAllByUserIdAndServiceId(userId,serviceId)
      .map(services => Ok(views.html.user(services)))
  }

  // DELETE  /users/:userId/favouriteServices/:serviceId
  def delete(userId:Int,serviceId:Int) = Action.async { implicit rs =>
    favouriteServiceRepo.delete(userId,serviceId).map(id => Ok(s"$id service deleted"))
  }

}
