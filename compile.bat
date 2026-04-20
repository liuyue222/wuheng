@echo off
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jre-11.0.21.9-hotspot
"C:\Program Files\Android\Android Studio\gradle\gradle-7.6\bin\gradle.bat" :app:compileDebugKotlin --no-daemon
