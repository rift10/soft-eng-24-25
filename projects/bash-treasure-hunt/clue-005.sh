#!/usr/bin/env bash

set -euo pipefail

tail -n1 $(grep -rl "needle" "puzzle/needle")