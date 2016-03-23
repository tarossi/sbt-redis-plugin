lazy val redisPlugin = file("..").getAbsoluteFile.toURI

lazy val root = Project("sample", file(".")) dependsOn redisPlugin