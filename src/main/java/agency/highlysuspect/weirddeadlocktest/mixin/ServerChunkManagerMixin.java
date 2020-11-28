package agency.highlysuspect.weirddeadlocktest.mixin;

import agency.highlysuspect.weirddeadlocktest.Init;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerChunkManager.class)
public class ServerChunkManagerMixin {
	@Shadow @Final @Mutable private Thread serverThread;
	
	@Inject(
		method = "getChunk(IILnet/minecraft/world/chunk/ChunkStatus;Z)Lnet/minecraft/world/chunk/Chunk;",
		at = @At("HEAD")
	)
	private void whenYouMomComeHomeAndGetTheChunk(int x, int z, ChunkStatus leastStatus, boolean create, CallbackInfoReturnable<Chunk> cir) {
		if(Thread.currentThread() != serverThread) {
			Init.ohNo(Thread.currentThread(), serverThread);
			
			if(Init.overwriteThread) {
				serverThread = Thread.currentThread();
			}
		}
	}
}
