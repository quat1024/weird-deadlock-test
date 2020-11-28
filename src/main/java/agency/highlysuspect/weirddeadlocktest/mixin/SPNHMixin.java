package agency.highlysuspect.weirddeadlocktest.mixin;

import agency.highlysuspect.weirddeadlocktest.Init;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerPlayNetworkHandler.class, priority = 950)
public class SPNHMixin {
	//Note that defaultRequire is 0 in my mixins.json, so i can have both of these injectors it's ok if one fails.
	//This is kinda goofy (there's no protection against *both* applying), but in this case, it works.
	
	@Shadow
	public ServerPlayerEntity player;
	
	//1.16.3-
	@Inject(
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/PlayerManager;broadcastChatMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V"
		),
		method = "onGameMessage", cancellable = true
	)
	private void handleCorporeaRequest(ChatMessageC2SPacket packet, CallbackInfo ci) {
		doThing(player, packet.getChatMessage(), "1.16.3");
	}
	
	//1.16.4
	@Inject(
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/PlayerManager;broadcastChatMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V"
		),
		method = "method_31286", cancellable = true
	)
	private void handleCorporeaRequestPog(String message, CallbackInfo ci) {
		doThing(player, message, "1.16.4");
	}
	
	@Unique
	private static void doThing(ServerPlayerEntity player, String message, String version) {
		String yeahWoo = StringUtils.normalizeSpace(message);
		if(yeahWoo.isEmpty()) return;
		Init.yeahWoo(player, yeahWoo, "1.16.3");
	}
}
