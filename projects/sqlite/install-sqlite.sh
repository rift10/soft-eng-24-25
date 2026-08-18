#!/usr/bin/env bash

set -euo pipefail

YEAR=2025
VERSION=3490000
JDBC_VERSION=3.49.0.0

# Get readline
sudo apt-get update
sudo apt-get install libreadline8 libreadline-dev

# Fetch the software from the Internet
wget "https://sqlite.org/$YEAR/sqlite-autoconf-$VERSION.tar.gz"

# Unpack it
tar xzf "sqlite-autoconf-$VERSION.tar.gz"

# Build it
cd "sqlite-autoconf-$VERSION/"
./configure --prefix=/usr --enable-readline
make

# Install it
sudo make install

# Clean up
rm -rf "sqlite-autoconf-$VERSION"*

# Download SQLite JDBC driver
wget "https://github.com/xerial/sqlite-jdbc/releases/download/$JDBC_VERSION/sqlite-jdbc-$JDBC_VERSION.jar"
