package models


import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Issue (
    number: Pk[Long],
    title: String,
    description: String
)

object Issue {
  
  def list: Seq[Issue] = {
    DB.withConnection { implicit c =>
      SQL("select * from issue").as(
          get[Pk[Long]]("number_") ~
          str("title") ~
          str("description") map {
            case nbr ~ title ~ description => Issue(nbr, title, description)
          } *
      )
    }
  } 
}