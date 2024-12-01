package chapter4

trait ErrorMonad[+A]

case class Success[A](a: A) extends ErrorMonad[A]

case class Failure(errorMessage: String) extends ErrorMonad[Nothing]

object ErrorMonad {

  def pure[A](a: A): ErrorMonad[A] = Success(a)

  def map[A, B](input: ErrorMonad[A])(f: A => B): ErrorMonad[B] = input match {
    case Success(value) => pure(f(value))
    case Failure(error) => Failure(error)
  }

  def flatMap[A, B](input: ErrorMonad[A])(f: A => ErrorMonad[B]): ErrorMonad[B] = input match {
    case Success(value) => f(value)
    case error@Failure(_) => error
  }

}


object TestingError extends App {
  val success: ErrorMonad[Int] = Success(4)
  val error: ErrorMonad[Int] = Failure("Error message")

  import ErrorMonad._
  println(ErrorMonad.map(success)(_ + 1))
  println(ErrorMonad.map(error)(_ + 1))

  println(ErrorMonad.flatMap(success)(x => Success(x * 2)))// Success(84)


}
