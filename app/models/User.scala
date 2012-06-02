package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class User(
    id: Long,
    name: String,
    email: String,
    password: String
)

object User {
  
  def user = {
    get[Long]("id") ~
    str("name") ~
    str("email") ~
    str("password") map {
      case id~name~email~password => User(id, name, email, password)
    }
  }
  
  def findById(id: Long) = {
    DB.withConnection { implicit c =>
      SQL(
        """
          select *
          from user_
          where id = {id} 
        """
      )
      .on("id" -> id)
      .as(user.singleOpt)
    }
  }
  
  def findByEmail(email: String) = {
    DB.withConnection { implicit c =>
      SQL(
        """
          select *
          from user_
          where email = {email} 
        """
      )
      .on("email" -> email)
      .as(user.singleOpt)
    }
  }
}