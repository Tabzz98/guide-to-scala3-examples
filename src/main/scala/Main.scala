
trait A  {
  val a: String
}

trait B {
  val b: Int
}

case class C(a: String, b: Int) 
  extends A with B

def f(c: A & B) = c.a + " & " + c.b

val a = new A { val a = "Some String" }
val b = new B { val b = 42 }

type E = Either[A, B]
val l: E = Left(a)
val r: E = Right(b)

def g(e: E): String = e match {
  case Left(a) => s"String value a: ${a.a}"
  case Right(b) => s"Int value b: ${b.b}"
}

type U = A | B

def h(v: A | Double | B | Boolean): String = v match {
  case a: A => s"String value a: ${a.a}"
  case d: Double => s"Double value d: $d"
  case b: B => s"Int value b: ${b.b}"
  case bool: Boolean => s"Boolean value bool: $bool"
}


enum WeekDay {
  case Sunday, Monday, Tuesday, Wednesday, 
       Thursday, Friday, Saturday
}

sealed trait VerboseLogicalExpression {
  import VerboseLogicalExpression._
  def eval: Boolean = this match {
    case ConstFactor(c) => c
    case NotFactor(c) => !c
    case Term(l, Some(r)) => l.eval && r.eval
    case Term(l, None) => l.eval
    case Expr(l, Some(r)) => l.eval || r.eval
    case Expr(l, None) => l.eval
  }
}
object VerboseLogicalExpression {
  sealed trait Factor extends VerboseLogicalExpression
  case class ConstFactor(value: Boolean) extends Factor
  case class NotFactor(value: Boolean) extends Factor
  case class Term(left: Factor, right: Option[Factor]) 
    extends VerboseLogicalExpression
  case class Expr(left: Term, right: Option[Term]) 
    extends VerboseLogicalExpression
}

enum LogicalExpression {
  case ConstFactor(value: Boolean)
  case NotFactor(value: Boolean)
  case Term(
    left: ConstFactor | NotFactor, 
    right: Option[ConstFactor | NotFactor]
  )
  case Expr(left: Term, right: Option[Term])

  def eval: Boolean = this match {
    case ConstFactor(c) => c
    case NotFactor(c) => !c
    case Term(l, Some(r)) => l.eval && r.eval
    case Term(l, None) => l.eval
    case Expr(l, Some(r)) => l.eval || r.eval
    case Expr(l, None) => l.eval
  }
}

import LogicalExpression._

val expr = new Expr(
  new Term(new ConstFactor(true), Some(new NotFactor(false))), 
  Some(new Term(new ConstFactor(false), None))
)
