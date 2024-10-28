package chapter1.printableTypeClasses

final case class Cat(name: String, age: Int, color: String)

object ExecutePrintable extends App {

  import PrintableInstances._
  val cat = Cat("Backie", 5, "White")
  Printable.print(cat)

  import PrintableSyntax._
  println("Using implicits...")
  cat.print
  cat.format

}

object PrintableSyntax {
  implicit class PrintableOps[A](a: A) {
    def format(implicit print: Printable[A]): String = print.format(a)
    def print(implicit printer: Printable[A]): Unit = println(format)
  }
}
