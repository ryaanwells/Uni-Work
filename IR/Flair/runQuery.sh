#/bin/bash

if [ $# != 1 ]; then
		echo "Usage ./runQuery OUTFILE";
		exit 1;
fi
./ops/flair.sh -q | grep MED- > $1
