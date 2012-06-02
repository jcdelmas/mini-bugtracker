package models


import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Issue (
    number: Long,
    user: User,
    title: String,
    description: String
)

object Issue {
  
  def issue = {
    get[Long]("number_") ~
    get[Long]("user_id") ~
    str("title") ~
    str("description") ~
    User.user map {
      case nbr ~ userId ~ title ~ description ~ user => Issue(nbr, user, title, description)
    }
  }
  
  def find(number: Long) = {
    DB.withConnection { implicit c =>
      SQL(
        """
          select * 
          from issue i
          inner join user_ u on i.user_id = u.id
          where i.number_ = {number}
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
          values (issue_seq.nextval, {userId}, {title}, {description})
        """
      ).on(
          "userId" -> issue.user.id, 
          "title" -> issue.title, 
          "description" -> issue.description
      ).executeInsert()
    }
  }
  
  def list: Seq[Issue] = {
    DB.withConnection { implicit c =>
      SQL(
        """
          select * 
          from issue i
          inner join user_ u on i.user_id = u.id
        """
      ).as(issue *)
    }
  }
}