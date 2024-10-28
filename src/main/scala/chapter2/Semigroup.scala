package chapter2

/*
  Semigroup only satisfy combine rule of Monoid not all data type have empty defined.
  Example is cats NonEmptyList, Boolean etc.

 */

trait Semigroup[A] {
  def combine(x: A, y: A): A
}
trait Monoids[A] extends Semigroup[A] {
  def empty: A
}
