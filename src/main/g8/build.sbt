lazy val root = (project in file(".")).
  settings(
    inThisBuild(
      List(
        organization := "$organization$",
        scalaVersion := "$scala_version$"
      )
    ),
    name := "$name$",
    libraryDependencies ++= {
      val akkaHttpVer = "$akka_http_version$"
      val akkaVer = "$akka_version$"
      val sangriaVer = "$sangria_version$"
      val overlayerVer = "$overlayer_version$"
      val sodaVer = "$soda_version$"
      val ahqlVer = "$ahql_version$"

      Seq(
        "io.github.d-exclaimation" %% "over-layer" % overlayerVer,
        "io.github.d-exclaimation" %% "ahql" % ahqlVer,
        "io.github.d-exclaimation" %% "soda" % sodaVer,

        "org.sangria-graphql" %% "sangria" % sangriaVer,
        "org.sangria-graphql" %% "sangria-spray-json" % "1.0.2",

        "com.typesafe.akka" %% "akka-http" % akkaHttpVer,
        "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVer,
        "com.typesafe.akka" %% "akka-actor-typed" % akkaVer,
        "com.typesafe.akka" %% "akka-stream" % akkaVer,
        "ch.megard" %% "akka-http-cors" % "1.1.2",
        "ch.qos.logback" % "logback-classic" % "1.2.6",

        "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVer % Test,
        "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVer % Test,
        "org.scalatest" %% "scalatest" % "3.2.9" % Test
      )
    }
  )
