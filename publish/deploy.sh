#!/usr/bin/env sh

if [[ ("${TRAVIS_PULL_REQUEST}" == "false") && \
      ("${TRAVIS_JDK_VERSION}" == "oraclejdk7") && \
      ("${TRAVIS_TAG}" != "") ]]; then

      echo Deploying to bintray with version $TRAVIS_TAG
      # Create .bintray/.credentials file
      mkdir -p ~/.bintray
      eval "echo \"$(< ./publish/credentials.template)\"" > ~/.bintray/.credentials

      sbt publish

      rm ~/.bintray/.credentials
else
    echo no
fi