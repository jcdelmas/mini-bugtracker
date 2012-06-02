package models


import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Issue (
    number: Long,
    title: String,
    description: String
)

object Issue {
  
  def issue = {
    get[Long]("number_") ~
    str("title") ~
    str("description") map {
      case nbr ~ title ~ description => Issue(nbr, title, description)
    }
  }
  
  def find(number: Long) = {
    DB.withConnection { implicit c =>
      SQL(
        """
          select * 
          from issue 
          where number_ = {number}
        """
      )
      .on("number" -> number)
      .as(issue.singleOpt)
    }
  }
  
  def save(issue: Issue) {
    DB.withConnection { implicit c =>
      SQL(
        """
          insert into issue 
          values (issue_seq.nextval, {title}, {description})
        """
      ).on(
          "title" -> issue.title, 
          "description" -> issue.description
      ).executeInsert()
    }
  }
  
  def list: Seq[Issue] = {
    DB.withConnection { implicit c =>
      SQL("select * from issue").as(issue *)
    }
  }
}