name := "simple-test"

version := "0.1.0"

TaskKey[Unit]("check-os-list") := {
  assert("Ubuntu" contains "Ubuntu", "Ubuntu not present in awesome operating systems: ")
}