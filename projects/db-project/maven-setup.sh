#!/usr/bin/env bash

#
# Set up a simple maven project.
#

set -euo pipefail

artifact=$(basename "$(pwd)")
package=$(echo "$GITHUB_USER.$artifact" | tr '-' '_')

mvn archetype:generate \
  -DgroupId="$package" \
  -DartifactId="$artifact" \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DarchetypeVersion=1.5 \
  -DinteractiveMode=false

# Not sure this is necessary but seems better.
sed -i 's/maven.compiler.release>17</maven.compiler.release>21</' "$artifact/pom.xml"

# Move everything up one level.
mv "$artifact"/* .
mv "$artifact"/.mvn .
rmdir "$artifact"


echo "Now you need to add dependencies on sqlite-jdbc from org.xerial and jdbi3-core from org.jdbi."
