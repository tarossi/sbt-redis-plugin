
lazy val root = (project in file("."))
  .settings(
    sbtPlugin := true,
    scalaVersion := "2.12.3",
    name := "redis-plugin",
    version in ThisBuild := "0.6.0",
    organization in ThisBuild := "eu.monniot.redis",
    resolvers ++= Seq(Resolver.jcenterRepo, Resolver.bintrayRepo("tarossi", "maven-public")),
    libraryDependencies ++= Seq("eu.monniot.redis" % "embedded-redis" % "1.5.2"),
    scriptedLaunchOpts := scriptedLaunchOpts.value ++ Seq("-Xmx1024M", "-Dplugin.version=" + version.value),
    scriptedBufferLog := false,
    publishMavenStyle := false,
    publishArtifact in Test := false,
    bintrayRepository := "ivy-public",
    bintrayOrganization in bintray := None,
    // Publish Information
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    homepage := Some(url("https://github.com/tarossi/sbt-redis-plugin")),
    scmInfo := Some(ScmInfo(
      browseUrl = url("https://github.com/tarossi/sbt-redis-plugin"),
      connection = "scm:git:git@github.com:tarossi/sbt-redis-plugin.git"
    )),
    developers := List(
      Developer(
        id = "fmonniot",
        name = "François Monniot",
        email = "francois@monniot.eu",
        url = url("http://francois.monniot.eu")
      ),
      Developer(
        id = "tarossi",
        name = "Tomás A. Rossi",
        email = "tarossi@gmail.com",
        url = url("https://github.com/tarossi")
      )
    )
  )


