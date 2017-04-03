package controllers

import play.api._
import play.api.mvc._

class Login extends Controller {

  def get = Action {
    Ok(views.html.login("babo"))
  }

  def post = Action { request =>
    val urlBody = request.body.asFormUrlEncoded
    urlBody.map { body =>
      if (body("username").head == "test" && body("password").head == "test")
        Ok("Logged in!")
      else
        Forbidden(s"""Wrong credentials!, You entered: username = ${body("username").head}, password = ${body("password").head}""")
    }.getOrElse {
      BadRequest("Expecting urlencoded request body")
    }
  }
}
