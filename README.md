weird deadlock test
===================

Came across a [bizarre issue](https://github.com/williewillus/botania-fabric-issues/issues/33) in botania-fabric but i don't think it's actually botania's fault. here's a test mod to try and isolate the issue.

note that the mixins.json has defaultRequire turned off, this is just to make it work on 1.16.3 and 1.16.4 since they changed chat messages a little bit. this was probably a bad idea, i'll clean it up tomorrow when it's not 1am to actually have two separate versions (cuz the whole thing fails silently if neither mixin applies)

sample output from a broken deadlock
```
[01:09:43] [Server thread/INFO] (weird_deadlock_test) Hello from the 1.16.3 mixin. You said asd
[01:09:43] [Server thread/INFO] (weird_deadlock_test) Successfully counted nearby items. There are 0 items within 256 blocks of you. Have a good day
[01:09:43] [Server thread/INFO] (weird_deadlock_test) OH NO ITS DIFFERENT!!! SCM#GETCHUNK IS TAKING THE WEIRD PATH!!!
[01:09:43] [Server thread/INFO] (weird_deadlock_test) current thread
[01:09:43] [Server thread/INFO] (weird_deadlock_test) toString: Thread[Server thread,5,main]
[01:09:43] [Server thread/INFO] (weird_deadlock_test) name:     Server thread
[01:09:43] [Server thread/INFO] (weird_deadlock_test) hash:     1352787976
[01:09:43] [Server thread/INFO] (weird_deadlock_test) state:    RUNNABLE
[01:09:43] [Server thread/INFO] (weird_deadlock_test) trace:    
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   java.lang.Thread.getStackTrace(Thread.java:1559)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   agency.highlysuspect.weirddeadlocktest.Init.logThread(Init.java:48)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   agency.highlysuspect.weirddeadlocktest.Init.ohNo(Init.java:37)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.server.world.ServerChunkManager.handler$zdm000$whenYouMomComeHomeAndGetTheChunk(ServerChunkManager.java:566)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.server.world.ServerChunkManager.getChunk(ServerChunkManager.java)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.world.chunk.ChunkManager.getWorldChunk(ChunkManager.java:14)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.world.World.getEntitiesByClass(World.java:720)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   vazkii.botania.common.impl.corporea.CorporeaHelperImpl.getSparkForBlock(CorporeaHelperImpl.java:144)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   vazkii.botania.common.block.tile.corporea.TileCorporeaBase.getSpark(TileCorporeaBase.java:24)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   vazkii.botania.common.block.tile.corporea.TileCorporeaIndex$InputHandler.onChatMessage(TileCorporeaIndex.java:376)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.server.network.ServerPlayNetworkHandler.handler$zke001$handleCorporeaRequest(ServerPlayNetworkHandler.java:3426)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.server.network.ServerPlayNetworkHandler.onGameMessage(ServerPlayNetworkHandler.java:1035)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket.apply(ChatMessageC2SPacket.java:31)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket.apply(ChatMessageC2SPacket.java:12)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.network.NetworkThreadUtils.method_11072(NetworkThreadUtils.java:20)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.server.ServerTask.run(ServerTask.java:17)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.util.thread.ThreadExecutor.executeTask(ThreadExecutor.java:136)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.util.thread.ReentrantThreadExecutor.executeTask(ReentrantThreadExecutor.java:22)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.server.MinecraftServer.executeTask(MinecraftServer.java:749)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.server.MinecraftServer.executeTask(MinecraftServer.java:1724)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.util.thread.ThreadExecutor.runTask(ThreadExecutor.java:109)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.server.MinecraftServer.method_20415(MinecraftServer.java:729)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.server.MinecraftServer.runTask(MinecraftServer.java:723)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.util.thread.ThreadExecutor.runTasks(ThreadExecutor.java:119)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.server.MinecraftServer.method_16208(MinecraftServer.java:709)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.server.MinecraftServer.runServer(MinecraftServer.java:663)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   net.minecraft.server.MinecraftServer.method_29739(MinecraftServer.java:224)
[01:09:43] [Server thread/INFO] (weird_deadlock_test)   java.lang.Thread.run(Thread.java:748)
[01:09:43] [Server thread/INFO] (weird_deadlock_test) server chunk manager thread
[01:09:43] [Server thread/INFO] (weird_deadlock_test) toString: Thread[Server thread,5,]
[01:09:43] [Server thread/INFO] (weird_deadlock_test) name:     Server thread
[01:09:43] [Server thread/INFO] (weird_deadlock_test) hash:     1444584825
[01:09:43] [Server thread/INFO] (weird_deadlock_test) state:    TERMINATED
[01:09:43] [Server thread/INFO] (weird_deadlock_test) trace:    
```
