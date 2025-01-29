#!/usr/bin/env bash

set -euo pipefail

cat $(find "puzzle/lots-of-files" -size +0 | tail -n1)
