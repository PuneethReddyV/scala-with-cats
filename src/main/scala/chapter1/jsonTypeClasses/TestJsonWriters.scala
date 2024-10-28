package chapter1.jsonTypeClasses

import JsonWriterInstances._

object TestJsonWriters extends App {
  private val person = Person("Puneeth", 1234567.3D, isActive = false)
  println("Without Implicits "+Json.toJson(person))
  import Implicits._
  println("With Implicits "+person.toJson)
  println(Json.toJson(Option("ABC")))
}

object Implicits {
  implicit class ValueToJson[A](value :A) {
    def toJson(implicit jsonWriter: JsonWriter[A]): Json = jsonWriter.write(value)
  }
}
