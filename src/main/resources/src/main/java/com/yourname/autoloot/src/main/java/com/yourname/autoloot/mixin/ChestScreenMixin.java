package com.yourname.autoloot.mixin;

import com.yourname.autoloot.AutoLootClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GenericContainerScreen.class)
public class ChestScreenMixin {

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        if (!AutoLootClient.enabled) return;

        GenericContainerScreen screen = (GenericContainerScreen)(Object)this;
        GenericContainerScreenHandler handler = screen.getScreenHandler();

        int containerSlots = handler.getRows() * 9;

        for (int i = 0; i < containerSlots; i++) {
            Slot slot = handler.getSlot(i);
            if (slot.hasStack()) {
                screen.getClient().interactionManager.clickSlot(
                    handler.syncId,
                    slot.id,
                    0,
                    SlotActionType.QUICK_MOVE,
                    screen.getClient().player
                );
            }
        }
    }
}
