# MarvelCharacters
Small android app that uses Marvel-API to render marvel characters along with comics, series and events where each one of them appear



In order to get the API working you must provide a gradle.properties file where both public and private key are provided.
The next text is an example of how the file should look like:

# Project-wide Gradle settings.
# IDE (e.g. Android Studio) users:
# Gradle settings configured through the IDE *will override*
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8

android.useAndroidX=true
kotlin.code.style=official
android.nonTransitiveRClass=true

MARVEL_API_PUBLIC_KEY="XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
MARVEL_API_PRIVATE_KEY="XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
