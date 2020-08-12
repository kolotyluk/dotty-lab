package net.kolotyluk.dotty.extras

import com.typesafe.config.Config
import com.typesafe.config.ConfigException

/* =Scala 3 Configuration Extras=
 *
 */
trait Configuration extends Logging {

  object PathBase extends Logging {
    var pathBase: String = null
    def setPathBase(path: String) = {
      pathBase = path
      // TODO log this later...
      // logger.info(s"pathBase set to ${pathBase}")
    }
  }

  def setPathBase(path: String) = {
    PathBase.setPathBase(path)
  }

  private def base(config: Config) = {
    try {
      if (PathBase.pathBase == null) {
        val message = "\n\n\nHave you forgotten to call config.setPathBase? Using pathBase = \"\" instead.\n\n\n"
        logger.error(message)
        throw new RuntimeException(message)
      } else {
          config.getConfig(PathBase.pathBase)
      }
    }  catch {
      case cause: com.typesafe.config.ConfigException.Missing ⇒
        logger.error(s"You need to define '${PathBase.pathBase}' in reference.conf or application.conf", cause)
        throw cause
      case cause: com.typesafe.config.ConfigException.WrongType ⇒
        logger.error("Internal problem with Typesafe Configuration handling", cause)
        throw cause
    }
  }

  /** =Fatal Configuration Error=
    * Indicates and unrecoverable configuration problem was encountered.
    * <p>
    * A ConfigurationError indicates an unrecoverable condition was encountered during configuration, and that the
    * application or service should be terminated. Configuration problems are usually human error when initially
    * setting up an application or service, so it's generally best to fail big, and fail early, so that the problem
    * can be remedied.
    *
    * @param path from pathBase, of configuration setting
    * @param value value found
    * @param message diagnosis and prognosis
    * @param cause underlying Throwable
    */
    class ConfigurationError(path: String, value: Any, message: String, cause: Throwable) extends Error {
      def this(path: String, value: Any, message: String) = this(path, value, message, null)
      logger.error(s"Configuration error at ${PathBase.pathBase}.$path = $value; check your reference.conf or application.conf settings")
      logger.error(message, cause)
    }
  
    lazy val config = com.typesafe.config.ConfigFactory.load

    lazy val javaEntrySet = config.entrySet

    lazy val scalaConfig = {
      val scalaMap = new scala.collection.mutable.HashMap[String, String]
      javaEntrySet.stream.forEach(entry => {scalaMap.put(entry.getKey,entry.getValue.render)})
      scalaMap
    }

    lazy val configurationReport = scalaConfig
      .toSeq
      .map{case (key,value) => s"${key} = ${value}"}
      .sortWith(_.compareTo(_) < 0) // this method does NOT use implicits, so we don't have to eanble 'implicit' in dotty
      .mkString("<configuration>\n\t", "\n\t", "\n</configuration>")

    //class ConfigurationException extends RuntimeException  {}

}