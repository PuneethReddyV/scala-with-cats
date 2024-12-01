package chapter4

object UsingCatsMonads extends App {
  import cats.Monad
  import cats.syntax.functor._ // for map
  import cats.syntax.flatMap._ // for flatMap

  def sumOfSqures[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] = for {
    x <- a
    y <- b
  } yield (x*x + y*y)
  //a.flatMap(x => b.map(y => x*x + y*y))


  import cats.instances.option._ // for Monad
  import cats.instances.list._   // for Monad
  println(sumOfSqures(Option(4), Option(5)))

  println(sumOfSqures(List(1, 2, 4), List(4, 5)))

  import cats.Id
  println(sumOfSqures(1: Id[Int], 3: Id[Int]))

  //Id let us use Monad, Functor methods as well
  val idsPure1 = Monad[Id].pure(3)
  val idsPure2 = Monad[Id].pure(4)

  println(idsPure1.flatMap(_ + 1))

}

trait IdsDuplicate {

  type ID[A] =  A

  def pure[A](a: A): ID[A] = a

  //def flatMap[A, B](input: ID[A])(f: A => ID[B]): ID[B] = f(pure(input)) ///????

 //def map[A, B](input: ID[A])(f: A => B): ID[B] = flatMap(input)(i => f(pure(i)))///*****

}
