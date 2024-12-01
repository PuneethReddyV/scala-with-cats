package chapter1.printableTypeClasses

trait Printable[A] {

  def format(value: A): String

  def contraMap[B](f: B => A):  Printable[B] = new Printable[B] {
    override def format(value: B): String = Printable.this.format(f(value))  ///****
  }
}

object Printable {

  def format[A](value: A)(implicit printer: Printable[A]): String = printer.format(value)

  def print[A](value: A)(implicit printer: Printable[A]): Unit = println(format(value))

}

object PrintableInstances {

  implicit val stringPrint: Printable[String] = new Printable[String] {
    override def format(value: String): String = value
  }

  implicit val intPrinter: Printable[Int] = new Printable[Int] {
    override def format(value: Int): String = value.toString
  }

  implicit val catsPrinter: Printable[Cat] = new Printable[Cat] {
    override def format(value: Cat): String = s"NAME is ${value.name} ${value.age} year-old ${value.color} cat."
  }

}
