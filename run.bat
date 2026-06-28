@echo off
cd /d "%~dp0"
javac --release 8 -cp "lib/sqlite-jdbc-3.53.1.0.jar" -d bin src/dao/Sql.java src/model/*.java src/view/*.java
java -cp "bin;lib/sqlite-jdbc-3.53.1.0.jar" view.App
pause