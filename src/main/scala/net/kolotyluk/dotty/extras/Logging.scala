package net.kolotyluk.dotty.extras

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

trait Logging {
  protected lazy val logger: Logger = LogManager.getLogger(this.getClass)
}

