#!/usr/bin/env bash

set -euo pipefail

jcount="0"
up="0"
dead="0"
dino="P"
obs="l"
ground="============================================================================================================================================="
dino_pos="20"
space="$(( ${#ground} - dino_pos - 1 ))"
cur_space="$space"
score="0"

getkey() {
    old_tty_settings=$(stty -g) # save old settings
    stty -echo
    stty -icanon time 0 min 0
    keypress=$(head -c1)
    stty "$old_tty_settings" # restore old settings
}

printdino() {
    printf "%0.s " $(seq 1 "$dino_pos")
    printf "%s" "$dino"
}

while [ "$dead" -eq 0 ]; do
    printf "%s" "$(( score / 121))" # one obstacle passes every 121 frames
    echo ""
    getkey

    case $keypress in
        m)  up="1" # press m to jump
            jcount="1" ;; # we start jumping so up jcount
    esac

    if [ "$up" -eq 0 ]; then # if player hasnt jumped
        echo ""
        printdino
    else # if player has jumped
        printdino
        echo ""
        printf "%0.s " $(seq 1 "$(( dino_pos + 1 ))") # compensate for space
    fi

    # print space before obstacle
    # shellcheck disable=SC2034
    for i in $(seq 1 "$cur_space"); do
        printf " "
    done

    # reset space before obstacle and check if dead
    if [ "$cur_space" -lt 1 ]; then
        cur_space="$space"
        if [ "$up" -eq 0 ]; then
            dead="1"
        fi
    fi
    echo "$obs"
    echo "$ground"

    # update spacing, score, and jumping
    ((cur_space--))
    ((score+=1))
    if [ "$jcount" -ge 1 ]; then
        ((jcount+=1))
    fi
    if [ "$jcount" -eq 8 ]; then # stop jumping after set amount of frames
        up="0"
        jcount="0"
    fi
done
