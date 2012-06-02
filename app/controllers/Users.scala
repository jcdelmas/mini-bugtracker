package controllers

import play.api.mvc._
import play.api.mvc.Security._
import models._
import views._

import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.data.validation.Constraints._

object Users extends Controller {

  def authForm = Form(
      tuple(
        "email" -> nonEmptyText,
        "password" -> nonEmptyText
      )
  )
  
  def showLoginForm = Action(
      Ok(html.user.login(authForm))
  )
  
  def authenticate = Action { implicit request =>
    authForm.bindFromRequest.fold(
      error => BadRequest(html.user.login(error)),
      auth => {
        User.findByEmail(auth._1).map { user =>
          if (user.password == auth._2) {
            Redirect(routes.Issues.list).withSession(
              username -> user.id.toString()
            )
          } else {
            BadRequest(html.user.login(authForm.fill(auth), Some("Invalid password")))
          }
        }.getOrElse {
          BadRequest(html.user.login(authForm.fill(auth), Some("Unknown email address")))
        }
      }
    )
  }
  
  def logout = Action { implicit request =>
  	Redirect(routes.Issues.list).withNewSession
  }
}