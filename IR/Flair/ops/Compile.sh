#!/bin/bash
# The Original Code is anyclass.sh

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
#        David Hannah <davidh@dcs.gla.ac.uk>
#


fullPath () {
        t='TEMP=`cd $TEMP; pwd`'
        for d in $*; do
                eval `echo $t | sed 's/TEMP/'$d'/g'`
        done
}


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
echo "Using Java $JAVA_HOME"

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


echo "******* Setting Classpath *******";
if [ ! -n "$FLAIRCLASSPATH" ]
then
for jar in ./lib/*.jar ./lib/*.zip; do
        if [ ! -n "$FLAIRCLASSPATH" ]
        then
                FLAIRCLASSPATH=$jar
        else
                FLAIRCLASSPATH=$FLAIRCLASSPATH:$jar
        fi
done
fi

echo "******* Compiling *******"
echo "javac -cp $FLAIRCLASSPATH `find . -name '*.java'`"
$JAVA_HOME/bin/javac -cp $FLAIRCLASSPATH `find . -name '*.java'`
cd ./src
$JAVA_HOME/bin/jar -cf ../lib/Flair.jar `find . -name '*.class'`
