package chapter1.jsonTypeClasses

/*
    Type classes have four
    1. main class(JSON)
    2. instances (JsObject, JsString, JsBoolean)
    3. type class implicit convertors
        a. JsonWriters
        b. Json object
    4. type class and instance implementors
 */

//1. Declaration and its child classes
sealed trait Json
final case class JsObject(get: Map[String, Json]) extends Json
final case class JsString(string: String) extends Json
case object JsNull extends Json
final case class JsBoolean(boolean: Boolean) extends Json
final case class JsNumber(boolean: Double) extends Json


trait JsonWriter[A] {
  def write(value: A): Json
}

object Json {
  def toJson[A](value: A)(implicit jsonWriter: JsonWriter[A]): Json = jsonWriter.write(value)
}