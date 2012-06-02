package controllers

import play.api.mvc._
import models._
import views._

object Issues extends Controller {

  def list = Action {
    Ok(html.listIssues(List(
        Issue(100, "Bug1", "Lorem Ipsum"),
        Issue(101, "Bug2", "Lorem Ipsum")
    )))
  }
}