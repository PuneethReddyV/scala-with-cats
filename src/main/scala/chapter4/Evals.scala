package chapter4

import scala.util.Random
import cats.Eval

/*

+-------------+-----------+---------------------+
| Scala       |  Cats     |  Properties         |
+-------------+-----------+---------------------+
| val         | Now       | Eager, Memoized     |
| def         | Always    | Lazy, not memoized  |
| lazy val    | Later     | Lazy, memoized      |
+-------------+-----------+---------------------+

*/

object Evals extends App {

  private val rand = Random
  val callByValueIsQuickAndMemorized = {
    println("I will be called ONLY once")
    rand.nextInt()
  }

  println(s"1. Call by 'VAL' is eager and memorized $callByValueIsQuickAndMemorized")
  println(s"2. Call by 'VAL' is eager and memorized $callByValueIsQuickAndMemorized")

  def callByNameIsLazyAndNotMemorized = {
    println("I will be called EVERY time")
    rand.nextInt()
  }
  println(s"3. Call by 'NAME' is lazy and NOT memorized $callByNameIsLazyAndNotMemorized")
  println(s"4. Call by 'NAME' is lazy and NOT memorized $callByNameIsLazyAndNotMemorized")

  lazy val callByLazyValueIsNotQuickAndMemorized = {
    println("I will be called ONLY once")
    rand.nextInt()
  }

  println(s"5. Call by 'LAZY VAL' is not eager and memorized $callByLazyValueIsNotQuickAndMemorized")
  println(s"6. Call by 'LAZY VAL' is not eager and memorized $callByLazyValueIsNotQuickAndMemorized")


  //Those above lines of code can be achieved using Eval in cats NOW, ALWAYS and LATER

  Eval.now("Called only once").map(println).value
  Eval.always{ println("Step 1"); "Hello" }
    .map{ str => println("Step 2"); s"$str world" }.value

  val ans = for {
    a <- Eval.now{ println("Calculating A"); 40 }
    b <- Eval.always{ println("Calculating B"); 2 }
  } yield {
    println("Adding A and B")
    a+b
  }

  println(s"Those above steps are not memorized so ${ans.value} evaluted everytime")

  val saying = Eval
    .always{ println("Step 1"); "The cat" }
    .map{ str => println("Step 2"); s"$str sat on" }
    .memoize
    .map{ str => println("Step 3"); s"$str the mat" }
  println(s"Those above steps UNTIL 3 are memorized so ${saying.value} evaluted everytime")
  println(s"Those above steps UNTIL 3 are memorized so ${saying.value} evaluted everytime")


  /*
  One useful property of Eval is that its map and flatMap methods are tram‐ polined.
   This means we can nest calls to map and flatMap arbitrarily without consuming stack frames.
   We call this property “stack safety”.
   */

  def factorial(n: BigInt): BigInt =
    if(n == 1) n else n * factorial(n - 1)

  //println("This step will blow up the stack "+factorial(50000).value)

  //We can rewrite the method using Eval to make it stack safe:
  def factorialWithEval(n: BigInt): Eval[BigInt] =
    if(n == 1) {
      Eval.now(n)
    } else {
      Eval.defer(factorialWithEval(n - 1).map(_ * n))
    }

  //Eval is a useful tool to enforce stack safety when working on very large com‐ putations
  // and data structures. However, we must bear in mind that trampolin‐ ing is not free.
  println("This step will NEVER blow up the stack "+factorialWithEval(50000).value)


//EXERCISE
  import cats.Eval

  private def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): Eval[B] = as match {
    case head :: tail =>
      Eval.defer(foldRight(tail, acc)(fn).map(fn(head, _)))
    case Nil =>
      Eval.now(acc)
  }

  // Example usage:
  val result = foldRight(List(1, 2, 3, 4), 0)(_ + _).value
  println(result) // Outputs: 10
}
