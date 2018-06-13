package models

import play.api.libs.json.Json

case class Cat(name: String, color: String)

object Cat{
  implicit val catFormar = Json.format[Cat]
}