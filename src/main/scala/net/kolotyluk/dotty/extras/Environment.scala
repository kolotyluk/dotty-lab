package net.kolotyluk.dotty.extras

import java.util.Map.Entry

//import scala.collection.JavaConverters._
import scala.collection.mutable

/** =Scala Environment Extras=
  * <p>
  * Some extra utilities for accessing the local system [[https://en.wikipedia.org/wiki/Environment_variable environment variables]].
  */
trait Environment {

  /** =Java Environment Variables=
    * Java friendly collection of environment variables.
    */

  lazy val javaEnvironment = System.getenv

  lazy val scalaEnvironment = {
    val scalaMap = new mutable.HashMap[String, String]
    val i = javaEnvironment.entrySet.iterator
    while (i.hasNext) {
      val e = i.next
      scalaMap.put(e.getKey,e.getValue)
    }
    scalaMap
  }


      /** =Environment Report=
      * <p>
      * Generate a report of known environment variables.
      * <p>
      * This is generally useful at application startup, and recommended before the logging system is invoked,
      * in case there are environment configuration issues leading to logging problems. For example
      * {{{
      * println(environment.getEnvironmentReport)
      * }}}
      */

  def getReport(): String = {
    scalaEnvironment
    .toSeq
    .map(entry => s"${entry._1} = ${entry._2}")
    .sortWith(_.compareTo(_) < 0)
    .mkString("<environment>\n\t", "\n\t", "\n</environment>")
  }
}