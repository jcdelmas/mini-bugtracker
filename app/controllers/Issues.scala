package controllers

import play.api.mvc._
import models._
import views._

import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.data.validation.Constraints._

object Issues extends Controller {

  def list = Action {
    Ok(html.listIssues(Issue.list))
  }
  
  def view(number: Long) = Action {
    Issue.find(number).map { issue =>
      Ok(html.viewIssue(issue))
    }.getOrElse(NotFound)
  }
  
  var issueForm: Form[Issue] = Form(
      mapping(
          "number" -> of[Long],
          "title" -> nonEmptyText,
          "description" -> text
      )(Issue.apply)(Issue.unapply)
  )
  
  def create = Action {
    Ok(html.editIssue(issueForm.fill(Issue(0, "", ""))))
  }
  
  def save = Action { implicit request =>
    issueForm.bindFromRequest.fold(
        errors => BadRequest(html.editIssue(errors)),
        issue => {
          Issue.save(issue)
          Redirect(routes.Issues.list())
        }
    )
  }
}