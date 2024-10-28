package chapter1.jsonTypeClasses

case class Person(name: String, sal: Double, isActive: Boolean)

object JsonWriterInstances {

  implicit val stringWriter: JsonWriter[String] = new JsonWriter[String] {
    override def write(value: String): Json = JsString(value)
  }

  implicit val booleanWriter: JsonWriter[Boolean] = new JsonWriter[Boolean] {
    override def write(value: Boolean): Json = JsBoolean(value)
  }

  implicit val doubleWriter: JsonWriter[Double] = new JsonWriter[Double] {
    override def write(value: Double): Json = JsNumber(value)
  }

  implicit val personWriter: JsonWriter[Person] = new JsonWriter[Person] {
    override def write(value: Person): Json = JsObject(Map(
      "name" -> JsString(value.name),
      "salary" -> JsNumber(value.sal),
      "is_active" -> JsBoolean(value.isActive
    )))
  }

  implicit def optionWriter[A](implicit writer: JsonWriter[A]): JsonWriter[Option[A]] = new JsonWriter[Option[A]] {
    override def write(value: Option[A]): Json =  value match {
      case Some(value) => writer.write(value)
      case None => JsNull
    }
  }
}
