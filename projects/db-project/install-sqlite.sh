#!/usr/bin/env bash

#
# Install an up-to-date SQLite if it's not already installed
#

set -euo pipefail

if version=$(sqlite3 --batch --version); then
    echo "sqlite3 ${version%% *} already installed"
    exit 0
fi

YEAR=2025
VERSION=3490000

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
cd ..
rm -rf "sqlite-autoconf-$VERSION"*
