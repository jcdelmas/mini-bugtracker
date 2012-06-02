package views.html.helper

package object custom {
  implicit val twitterBootstrapField = new FieldConstructor {
    def apply(elts: FieldElements) = customFieldConstructor(elts)
  }
}