package chapter4

object EitherMonad extends App {


  val either1 = Right(32)
  val either2 = Right(10)

  import cats.syntax.either._ // for map and flatMap

  val bothEithers = for {
    a <- either1
    b <- either2
  } yield a + b

  println("1. "+bothEithers)

  import cats.syntax.either._ // for asRight
  val aEither: Either[String, Int] = 3.asRight[String]
  val bEither: Either[String, Int] = 10.asRight[String]

  println { for {
    x <- aEither
    y <- bEither
  } yield x*x + y*y }

  //above `asRight` helps us in foldLeft type of operations as in normal situations seed value is right
  // combine operation contains both right and left, below code solves this issue
  def countPositives(l: List[Int]): Either[String, Int] = l.foldLeft(0.asRight[String])(
    (count, ele) => if(ele > 0) count.map(_ + 1) else Left("Negative. Stopping!")
  )

  println(countPositives(List(2,4,6,8,9)))
  println(countPositives(List(2,4,6,8,10)))

  //asLeft

  val fromLeft: Int = "error".asLeft[Int].getOrElse(0)
  val fromLeftToRight: Either[String, Int] = "error".asLeft[Int].orElse(2.asRight[String])

  //`ensure` method allows us to check whether the right‐hand value satisfies a predicate:
  val ensuredValue: Either[String, Int] = (-1).asRight[String].ensure("Must be non-negative!")(_ > 0)
  println("ensuredValue "+ensuredValue)


  //`leftMap`, `RightMap` and `biMap`, `swap`
  println("lMap " + "leftValue".asLeft[Int].leftMap(_.reverse))
  println("biMap with Right projection " + 10.asRight[String].bimap(_.reverse, _ * 5))
  println("biMap with Left projection " + "foo".asLeft[Int].bimap(_.reverse, _ * 5))
  println("swap " + "foo".asLeft[Int].swap)


}
