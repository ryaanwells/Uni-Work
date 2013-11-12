#!/bin/bash
# The Original Code is anyclass.sh
#
# Terrier - Terabyte Retriever
# Webpage: http://ir.dcs.gla.ac.uk/terrier 

# The contents of this file are subject to the Mozilla Public
# License Version 1.1 (the "License"); you may not use this file
# except in compliance with the License. You may obtain a copy of
# the License at http://www.mozilla.org/MPL/
#
# Software distributed under the License is distributed on an "AS
# IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
# implied. See the License for the specific language governing
# rights and limitations under the License.
#
#
# The Initial Developer of the Original Code is the University of Glasgow.
# Portions created by The Initial Developer are Copyright (C) 2004 
# the initial Developer. All Rights Reserved.
#
# Contributor(s):
#    Vassilis Plachouras <vassilis@dcs.gla.ac.uk> (original author)
#    Craig Macdonald <craigm@dcs.gla.ac.uk>
# Modified
#	 David Hannah <davidh@dcs.gla.ac.uk>
#

fullPath () {
        t='TEMP=`cd $TEMP; pwd`'
        for d in $*; do
                eval `echo $t | sed 's/TEMP/'$d'/g'`
        done
}


echo "***** Starting Flair *****"
#echo $JAVA_HOME
#echo `which java`
if [ ! -n "$JAVA_HOME" ]
then
	 #where is java?
        TEMPVAR=`which java`
        #j2sdk/bin folder is in the dir that java was in
        TEMPVAR=`dirname $TEMPVAR`
        #then java install prefix is folder above
        JAVA_HOME=`dirname $TEMPVAR`
        echo "Setting JAVA_HOME to $JAVA_HOME"
fi
#setup FLAIR_HOME
if [ ! -n "$FLAIR_HOME" ]
then
        #find out where this script is running
        TEMPVAR=`dirname $0`
        #make the path abolute
        fullPath TEMPVAR
        #terrier folder is folder above
        FLAIR_HOME=`dirname $TEMPVAR`
        echo "Setting FLAIR_HOME to $FLAIR_HOME"
fi


#setup FLAIR_ETC
if [ ! -n "$FLAIR_ETC" ]
then
        FLAIR_ETC=$FLAIR_HOME/etc
	echo "Setting FLAIR_ETC to $FLAIR_ETC"
fi

#Setting ClassPath
for jar in ./lib/*.jar ./lib/*.zip; do
   if [ ! -n "$FLAIR_CLASSPATH" ]
        then
                FLAIR_CLASSPATH=$jar
        else
                FLAIR_CLASSPATH=$FLAIR_CLASSPATH:$jar
        fi
done

FLAIR_CLASSPATH=$FLAIR_CLASSPATH:src/
JAVA_OPTIONS=

echo $JAVA_HOME/bin/java -Xmx512M $JAVA_OPTIONS $FLAIR_OPTIONS -Dflair.etc=$FLAIR_ETC -Dflair.home=$FLAIR_HOME -cp $FLAIR_CLASSPATH $@

$JAVA_HOME/bin/java -Xmx512M $JAVA_OPTIONS $FLAIR_OPTIONS \
	 -Dflair.etc=$FLAIR_ETC \
         -Dflair.home=$FLAIR_HOME \
	 -cp $FLAIR_CLASSPATH $@
