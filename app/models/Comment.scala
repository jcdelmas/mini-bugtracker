package models

import java.util.Date

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Comment (
    id: Long,
    issueNumber: Long,
    user: User,
    text: String,
    timestamp: Date
)

object Comment {
  
  def comment = {
    get[Long]("id") ~
    get[Long]("issue_number") ~
    get[Long]("user_id") ~
    str("text") ~
    date("timestamp_") ~
    User.user map {
      case id ~ issueNbr ~ userId ~ text ~ timestamp ~ user => Comment(id, issueNbr, user, text, timestamp)
    }
  }
  
  def findByIssue(issueNumber: Long) = {
    DB.withConnection { implicit c =>
      SQL(
        """
          select *
          from comment_ c
          inner join user_ u on c.user_id = u.id
          where c.issue_number = {issueNumber}
        """
      )
      .on("issueNumber" -> issueNumber)
      .as(comment *)
    }
  }
  
  def save(issueNumber: Long, user: User, text: String) = {
    DB.withConnection { implicit c =>
      SQL(
        """
          insert into comment_
          values (comment_seq.nextval, {issueNbr}, {userId}, {text}, CURRENT_TIMESTAMP)
        """
      ).on(
        "issueNbr" -> issueNumber,
        "userId" -> user.id,
        "text" -> text
      ).executeInsert()
    }
  }
}