package agency.highlysuspect.weirddeadlocktest;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Init implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("weird_deadlock_test");
	
	@Override
	public void onInitialize() {
		
	}
	
	public static boolean overwriteThread = false;
	
	public static void yeahWoo(ServerPlayerEntity player, String message, String version) {
		LOGGER.info("Hello from the " + version + " mixin. You said " + message);
		
		if(message.startsWith("toggle")) {
			overwriteThread ^= true;
			LOGGER.info("overwriteThread is now " + overwriteThread + ". (persists across relogs)");
		}
		
		List<ItemEntity> items = player.world.getEntitiesByType(EntityType.ITEM, player.getBoundingBox().expand(256), e -> true);
		
		LOGGER.info("Successfully counted nearby items. There are " + items.size() + " items within 256 blocks of you. Have a good day");
	}
	
	public static void ohNo(Thread current, Thread server) {
		LOGGER.info("OH NO ITS DIFFERENT!!! SCM#GETCHUNK IS TAKING THE WEIRD PATH!!!");
		logThread("current thread", current);
		logThread("server chunk manager thread", server);
	}
	
	private static void logThread(String name, Thread a) {
		LOGGER.info(name);
		LOGGER.info("toString: " + a);
		LOGGER.info("name:     " + a.getName());
		LOGGER.info("hash:     " + System.identityHashCode(a));
		LOGGER.info("state:    " + a.getState());
		LOGGER.info("trace:    ");
		for(StackTraceElement t : a.getStackTrace()) {
			LOGGER.info("  " + t);
		}
	}
}
