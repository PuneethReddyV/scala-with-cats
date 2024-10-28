package chapter1

import cats.{Eq, Show}
import cats.implicits.toShow
import chapter1.printableTypeClasses.Cat

import java.util.Date
import cats.Eq._
import cats.implicits._
import cats.syntax.eq._ // for === and =!=
import cats.syntax.option._ // for some and none
import cats.syntax.eq._
import cats.instances.string._ // For Eq instance of String
import cats.instances.int._    // For Eq instance of Int


object CatsInstancesLikeShowAndEq extends App {

  //1
  implicit val dateToStringV1: Show[Date] = new Show[Date] {
    override def show(t: Date): String = s"${t.getTime} ms since 1971"
  }

  //2
  val dateToStringV2 = Show.show[Date](d => s"${d.getTime} ms since 1971")


  implicit val catsToString = Show.show[Cat](value => s"NAME is ${value.name} ${value.age} year-old ${value.color} cat.")

  println(new Date().show)
  println(Cat("Micky", 5, "Brown").show)


  val cat1 = Cat("Garfield",   38, "orange and black")
  val cat2 = Cat("Heathcliff", 33, "orange and black")
  val optionCat1: Option[Cat] = Option(cat1)
  val optionCat2: Option[Cat] = Option.empty

  implicit val catsComparator: Eq[Cat] = Eq.instance[Cat]((c1, c2) => {
    import cats.instances.string._
    //Eq[String].eqv(c1.color, c2.color) && c1.name === c2.name && Eq.eqv(c1.age, c2.age) ???
    Eq[String].eqv(c1.color, c2.color) && Eq.eqv(c1.name, c2.name) && Eq.eqv(c1.age, c2.age)

  } )

  println(Eq[Cat].eqv(cat1, cat2))
  //println(cat1 === cat2) //???
 // println((optionCat1: Option[Cat]) === (cat2 : Option[Cat])) ???


}




