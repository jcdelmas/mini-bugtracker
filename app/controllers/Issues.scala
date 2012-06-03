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

  def getUser(implicit request: Request[AnyContent]) = {
    session.get(username).flatMap(id => User.findById(id.toLong))
  }
  
  def UserAction(f: Option[User] => Request[AnyContent] => Result) = {
    Action { implicit request =>
      f(getUser)(request)
    }
  }
  
  def AuthAction(f: User => Request[AnyContent] => Result) = {
    Action { implicit request =>
      getUser.map { user =>
        f(user)(request)
      }.getOrElse {
        Unauthorized
      }
    }
  }
  
  def list = UserAction { implicit user => request =>
    Ok(html.issue.list(Issue.list))
  }
  
  def view(number: Long) = doView(number, commentForm.fill(""), Ok)
  
  def doView(issueNumber: Long, form: => Form[String], status: Status) = UserAction { implicit user => request =>
    Issue.find(issueNumber).map { issue =>
      status(
          html.issue.view(
              issue, 
              Comment.findByIssue(issue.number),
              form
          )
      )
    }.getOrElse(NotFound)
  }
  
  val commentForm = Form(
      single("text" -> nonEmptyText)
  ) 
  
  val issueForm = Form(
      tuple(
          "number" -> of[Long],
          "title" -> nonEmptyText,
          "description" -> text
      )
  )
  
  def create = AuthAction { implicit user => implicit request =>
    Ok(html.issue.edit(issueForm.fill(0, "", "")))
  }
  
  def save = AuthAction { implicit user => implicit request =>
    issueForm.bindFromRequest.fold(
        errors => BadRequest(html.issue.edit(errors)),
        issue => {
          Issue.save(Issue(0, user, issue._2, issue._3))
          Redirect(routes.Issues.list())
        }
    )
  }
  
  def saveComment(issueNumber: Long) = AuthAction { implicit user => implicit request =>
    commentForm.bindFromRequest.fold(
        errors => doView(issueNumber, errors, BadRequest)(request),
        commentText => {
          Comment.save(issueNumber, user, commentText)
          Redirect(routes.Issues.view(issueNumber))
        }
    )
  }
}