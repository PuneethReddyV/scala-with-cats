package chapter3

/*
  Functors needs to satisfy only one condition i.e. map convert from A to B type.

  We should think of map not as an iteration pattern, but as a way of sequencing computations
  on values ignoring some complication dictated by the relevant data type:
  • Option  — the value may or may not be present;
  • Either  — there may be a value or an error;
  • List    — there may be zero or more values.

  trait Functor[F[_]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
  }
 */




object TestChapter3 extends App {
  import cats.instances.function._ // for Functor
  import cats.syntax.functor._     // for map

  val stringToInt: String => Int = { string => string.length }

  val intToBoolean: Int => Boolean = { int => int % 2 == 0 }

    println((stringToInt map intToBoolean)("composition using map"))
    println((stringToInt andThen intToBoolean)("composition using andThen"))
    println((intToBoolean compose stringToInt)("composition using compose"))
    println(intToBoolean(stringToInt("composition written out by hand")))


  //*************************************************************************************
    import cats.Functor
    import cats.instances.list
    import cats.instances.option
  val list1: List[Int] = List(1,2,3)
  println(Functor[List].map(list1)(_ + 1))

  val option = Some("testing")
  println(Functor.apply[Option].map(option)(_.length))

  //Functor provides a method called lift, which converts a function of type A => BtoonethatoperatesoverafunctorandhastypeF[A] => F[B]:
  val optionFuctorUsingLift: Option[String] => Option[Int] = Functor[Option].lift(stringToInt)
  println(optionFuctorUsingLift(option))

  //The as method is the other method you are likely to use. It replaces with value inside the Functor with the given value.
  println(Functor[List].as(list1, "ReplacedByStrings"))

  //This is how cats create a Functor i.e. implicitly bringing map into the scope.
  implicit class FunctorOps[F[_], A](start: F[A]) {
    def maps[B](f: A => B)  //used maps instead of map as they are implicit issue at line 28!
              (implicit functor: Functor[F]): F[B] = //??? Functor[F]?
      functor.map(start)(f)
  }

  case class Box[A](value: A)
  val box = Box("This is Box")
  //below line throws compilation error as there is no implicit in scope as per FunctorOps[Box]
  //println(Functor[Box].map(box)(stringToInt))
  //so lets bring one Box functor to implicit scope
  implicit val boxFunctor: Functor[Box] = new Functor[Box] {
    override def map[A, B](fa: Box[A])(f: A => B): Box[B] = Box(f(fa.value))
  }
  println(Functor[Box].map(box)(stringToInt))
}
