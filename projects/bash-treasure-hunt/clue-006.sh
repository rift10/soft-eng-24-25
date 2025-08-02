#!/usr/bin/env bash

set -euo pipefail

tail -n1 $(grep -rL "hay" "puzzle/haystack")