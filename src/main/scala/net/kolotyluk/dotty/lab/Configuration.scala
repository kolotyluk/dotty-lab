package net.kolotyluk.dotty.lab

import com.typesafe.config.Config

//import java.net.{InetAddress, UnknownHostException}

/** =Local Configuration=
  * Central point of all leaderboard configuration
  * <p>
  * Extends [[net.kolotyluk.scala.extras.Configuration]]
  * that extends [[https://github.com/lightbend/config Typesafe Config]], so that pattern should be followed.
  */
trait Configuration extends net.kolotyluk.dotty.extras.Configuration {

  setPathBase("net.kolotyluk.dotty")

  extension (c: Config)
    def getAnswer: String = c.getString("net.kolotyluk.dotty.extras.config.answer")

}