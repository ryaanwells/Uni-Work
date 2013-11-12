@echo off
REM Terrier - Terabyte Retriever
REM Webpage: http://ir.dcs.gla.ac.uk/terrier
REM Contact: terrier@dcs.gla.ac.uk
REM
REM The contents of this file are subject to the Mozilla Public
REM License Version 1.1 (the "License"); you may not use this file
REM except in compliance with the License. You may obtain a copy of
REM the License at http://www.mozilla.org/MPL/
REM
REM Software distributed under the License is distributed on an "AS
REM IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
REM implied. See the License for the specific language governing
REM rights and limitations under the License.
REM
REM The Original Code is anyclass.bat
REM
REM The Initial Developer of the Original Code is the University of Glasgow.
REM Portions created by The Initial Developer are Copyright (C) 2004-2008
REM the initial Developer. All Rights Reserved.
REM
REM Contributor(s):
REM   Craig Macdonald <craigm@dcs.gla.ac.uk> (original author)
REM   Modified David Hannah


if "Windows_NT"=="%OS%" setlocal

rem keep %0 in case we overwrite
SET PROGRAM=%0
rem SCRIPT contains the full path filename of this script
SET SCRIPT=%~f0
rem BIN contains the path of the BIN folder
SET BIN=%~dp0

set COLLECTIONPATH=%~f1

REM --------------------------
REM Derive FLAIR_HOME, FLAIR_ETC, FLAIR_LIB
REM --------------------------

if defined FLAIR_HOME goto terrier_etc
CALL "%BIN%\fq.bat" "%BIN%\.."
SET FLAIR_HOME=%FQ%
echo Set FLAIR_HOME to be %FLAIR_HOME%

:terrier_etc
if defined FLAIR_ETC goto terrier_lib
SET FLAIR_ETC=%FLAIR_HOME%\etc

:terrier_lib
if defined FLAIR_LIB goto classpath
SET FLAIR_LIB=%FLAIR_HOME%\lib

:classpath

REM ------------------------
REM -- Build up class path 
REM ------------------------
call "%BIN%\lcp.bat" %CLASSPATH%
SET LOCALCLASSPATH=
FOR %%i IN ("%FLAIR_LIB%\*.jar") DO call "%BIN%\lcp.bat" "%%i"

SET LOCALCLASSPATH=%LOCALCLASSPATH%src\

REM ------------------------
REM -- Run TRECTerrier
REM ------------------------
java -Xmx512M -Dflair.home="%FLAIR_HOME%" -Dflair.etc="%FLAIR_ETC%" -cp %LOCALCLASSPATH% %JAVA_OPTIONS% %FLAIR_OPTIONS% uk.ac.gla.mir.flair.application.Application %*

if "Windows_NT"=="%OS%" endlocal
