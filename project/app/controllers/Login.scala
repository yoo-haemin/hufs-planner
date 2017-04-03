package controllers

import play.api._
import play.api.mvc._

class Login extends Controller {

  def get = Action {
    Ok(views.html.login())
  }

  def post = Action { request =>
    val urlBody = request.body.asFormUrlEncoded
    urlBody.map { body =>
      if (body("username")(0) == "test" && body("password")(0) == "test")
        Ok("Logged in!")
      else
        Forbidden(s"""Wrong credentials!, You entered: username = ${body("username")(0)}, password = ${body("password")(0)}""")
    }.getOrElse {
      BadRequest("Expecting urlencoded request body")
    }
  }
}
