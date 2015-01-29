#!/bin/sh

gson=( "2.3.1" "2.3" "2.2.4" "2.2.3" "2.2.2" "2.2.1" "2.2" "2.1" "2.0" )
vertx=( "2.1.5" "2.1.4" "2.1.3" "2.1.2" "2.1.1" "2.1" "2.0.2-final" "2.0.1-final" "2.0.0-final" )
json=( "20141113" "20140107" "20131018" "20090211" "20080701" "20070829" )


for g in "${gson[@]}"
do
    for v in "${vertx[@]}"
    do
        for j in "${json[@]}"
        do
            mvn clean test -U -q -Dsurefire.printSummary=false \
            -Dversion.gson=$g \
            -Dversion.vertx-core=$v \
            -Dversion.json=$j \
            > /dev/null
            if [ $? -ne 0 ];
            then
                echo "GSON($g), Vert.x($v), json.org($j) -> ERROR($?)"
            else
                echo "GSON($g), Vert.x($v), json.org($j) -> success"
            fi
        done
    done
done