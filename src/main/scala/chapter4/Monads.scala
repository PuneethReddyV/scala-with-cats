package chapter4


/*
    Every monad is also a funtor.
    Monad must satisfy 1. pure and 2. flatmap
      1. pure  => def pure[A](value: A): F[A]   ALSO known as APPLICATIVE
      2. flatmap => def flatMap[B](value: F[A])(f: A=> B): F[B]

 */

trait Monads[F[_]] {

  def pure[A](a: A): F[A]

  def flatMap[A, B](input: F[A])(f: A => F[B]): F[B]

  def map[A, B](input: F[A])(func: A => B): F[B] = flatMap(input)(a => pure(func(a))) ///*****

}
