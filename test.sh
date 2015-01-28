#!/bin/sh

vertx=( "2.1.5" "2.1.4" "2.1.3" "2.1.2" "2.1.1" "2.1" "2.0.2-final" "2.0.1-final" "2.0.0-final" )
gson=( "2.3.1" "2.3" "2.2.4" "2.2.3" "2.2.2" "2.2.1" "2.2" "2.1" "2.0" )


for v in "${vertx[@]}"
do
    for g in "${gson[@]}"
    do
        mvn clean test -U -q -Dsurefire.printSummary=false -Dversion.vertx-core=$v -Dversion.gson=$g > /dev/null
        if [ $? -ne 0 ];
        then
            echo "Vert.x($v), GSON($g) -> ERROR($?)"
        else
            echo "Vert.x($v), GSON($g) -> success"
        fi
    done
done