package example
import cats.syntax.all._

object Hello extends Greeting with App {
  println(greeting |+|  "effects")
}

trait Greeting {
  lazy val greeting: String = "hello "
}
