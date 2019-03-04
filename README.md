# Idea
Implementation is based on binary search with strong range limitation.

# Content
This repo contains sources as __maven project__ and compiled java classes in '__bin__' folder

# Run
Use following command, from current directory, to execute application:

__java AnagramApplication dict.txt word__

Timing is shown in microseconds (µs) and looks like for word 'ahta'

__2027,taha__

# Notes
This application can work much faster if:
 * Repeat operation without JVM restart
 * Make it case sensitive
 * Keep case insensitive but resort initial dictionnary
