#!/usr/bin/env bash

set -euo pipefail

cat "puzzle/rot13.txt" | tr "A-Za-z" "N-ZA-Mn-za-m"