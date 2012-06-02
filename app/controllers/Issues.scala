package controllers

import play.api.mvc._
import models._
import views._

object Issues extends Controller {

  def list = Action {
    Ok(html.listIssues(Issue.list))
  }
}