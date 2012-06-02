package controllers

import play.api.mvc._
import play.api.mvc.Security._
import models._
import views._

import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.data.validation.Constraints._

object Issues extends Controller {

  def UserAction(f: Option[User] => Request[AnyContent] => Result): Action[AnyContent] = {
    Action { implicit request =>
      val user = session.get(username).flatMap(id => User.findById(id.toLong))
      f(user)(request)
    }
  }
  
  def list = UserAction { implicit user => request =>
    Ok(html.issue.list(Issue.list))
  }
  
  def view(number: Long) = UserAction { implicit user => request =>
    Issue.find(number).map { issue =>
      Ok(html.issue.view(issue))
    }.getOrElse(NotFound)
  }
  
  var issueForm: Form[Issue] = Form(
      mapping(
          "number" -> of[Long],
          "title" -> nonEmptyText,
          "description" -> text
      )(Issue.apply)(Issue.unapply)
  )
  
  def create = UserAction { implicit user => implicit request =>
    Ok(html.issue.edit(issueForm.fill(Issue(0, "", ""))))
  }
  
  def save = UserAction { implicit user => implicit request =>
    issueForm.bindFromRequest.fold(
        errors => BadRequest(html.issue.edit(errors)),
        issue => {
          Issue.save(issue)
          Redirect(routes.Issues.list())
        }
    )
  }
}