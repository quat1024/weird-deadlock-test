weird deadlock test
===================

Came across a [bizarre issue](https://github.com/williewillus/botania-fabric-issues/issues/33) in botania-fabric but i don't think it's actually botania's fault. here's a test mod to try and isolate the issue.

note that the mixins.json has defaultRequire turned off, this is just to make it work on 1.16.3 and 1.16.4 since they changed chat messages a little bit.

you might also have to use dev jars (idk if it *actually* works on 1.16.3 and 1.16.4 lol)