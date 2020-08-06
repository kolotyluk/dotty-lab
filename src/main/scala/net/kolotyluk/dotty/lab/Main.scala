package net.kolotyluk.dotty.lab

import net.kolotyluk.dotty.extras.Environment
import net.kolotyluk.dotty.extras.Logging

/** =dotty-lab main entry point=
  *
  *
  */

object Main extends
  App
    with Environment
    with Logging {

    println("hello dotty")

    println(getReport())

    logger.info("logging now...")

}