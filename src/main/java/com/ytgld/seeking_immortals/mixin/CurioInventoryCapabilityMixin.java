package com.ytgld.seeking_immortals.mixin;

//import com.ytgld.seeking_immortals.Handler;
//import com.ytgld.seeking_immortals.init.Items;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//import top.theillusivec4.curios.api.SlotResult;
//import top.theillusivec4.curios.common.capability.CurioInventoryCapability;
//
//import java.util.Optional;
//import java.util.function.Predicate;
//
//@Mixin(CurioInventoryCapability.class)
//public abstract class CurioInventoryCapabilityMixin {
//
//
//    @Shadow public abstract Optional<SlotResult> findFirstCurio(Predicate<ItemStack> filter);
//
//    @Inject(at = @At("RETURN"), method = "findFirstCurio(Lnet/minecraft/world/item/Item;)Ljava/util/Optional;", cancellable = true)
//    private void findFirstCurio(Item item, CallbackInfoReturnable<Optional<SlotResult>> cir){
//        Optional<SlotResult> optionalSlotResult =  findFirstCurio((stack)->{
//            return stack.is(Items.disintegrating_stone.get());
//        });
//
//        if (optionalSlotResult.isPresent()) {
//            cir.setReturnValue(Optional.empty());
//        }
//    }
//}
