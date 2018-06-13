package controllers


import javax.inject._
import dao.CatDAO
import models.Cat
import play.api.Logger
import play.api.libs.json.{Json, Writes}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class Application @Inject() (cc: ControllerComponents, catDAO: CatDAO)(implicit executionContext: ExecutionContext)
  extends AbstractController(cc) {

  populateDate()

  def index() = Action.async { implicit request: Request[AnyContent] =>

    val fCats: Future[Seq[Cat]] = catDAO.all()

    fCats.map(s => Ok( Json.toJson(s) ))
  }

  def add() = Action.async(parse.json[Cat]) { request =>
    println("add: "+request.body)
    insertCat(request.body)
  }

  private def insertCat(cat: Cat): Future[Result] = {
    Logger.info("Entro a insertCat()"+catDAO.insert(cat))
    catDAO.insert(cat)
      .map(_ => Ok("guardo"))
      .recoverWith {
        case _: Exception => Future.successful( InternalServerError("No pudo guardarse el registro") )
      }
  }

  private def populateDate() {

    Logger.info("Entro a populateDate()")
    insertCat(new Cat("Expresso", "adsa"))
    insertCat(new Cat("Tostado", "sads"))
    insertCat(new Cat("Negro", "sad"))
    Logger.info("Salio a populateDate()")
  }
}
