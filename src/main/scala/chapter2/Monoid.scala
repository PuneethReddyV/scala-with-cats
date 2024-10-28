package chapter2

/*
  Monoids needs to two basic rules namely
    1. identity          Example: 0 for integer, "" for string and None for Option
    2. associative laws  Example: (1 + 2) + 3 = 1 + (2 + 3)
 */

trait Monoid[A] {
  def empty: A
  def combine(a: A, b: A): A
}

object Monoid {
   def isAssociativeLaw[A](a: A, b: A, c: A)(implicit m: Monoid[A]): Boolean =
     m.combine(a, m.combine(b, c )) == m.combine(m.combine(a, b), c)

  def isIdentityLaw[A](x: A)(implicit m: Monoid[A]): Boolean = m.combine(x, m.empty) == x && x == m.combine(m.empty, x)
}

object Test extends App {
  import cats.Monoid
  import cats.instances._ //for monoid

  println(cats.Monoid[String].combine("Hello", "Cats"))
  println(cats.Monoid[String].empty)

  import cats.instances.option._
  println(cats.Monoid[Option[Int]].combine(Some(4), None))

  import cats.syntax.semigroup._ // for |+|
  val stringResult = "Hi " |+| "there" |+| cats.Monoid[String].empty
  println(stringResult)

  import cats.instances.map._ // for Monoid
  val map1 = Map("a" -> 1, "b" -> 2)
  val map2 = Map("b" -> 3, "d" -> 4)
  println(map1 |+| map2)

  val tuple1 = ("hello", 123)
  val tuple2 = ("world", 321)
  println(tuple1 |+| tuple2)


}