package com.ytgld.seeking_immortals.event.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ytgld.seeking_immortals.Handler;
import com.ytgld.seeking_immortals.event.old.AdvancementEvt;
import com.ytgld.seeking_immortals.init.DataReg;
import com.ytgld.seeking_immortals.init.Items;
import com.ytgld.seeking_immortals.init.LootReg;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Map;
import java.util.function.Supplier;

public class DungeonLoot extends LootModifier {
    public static final Supplier<MapCodec<DungeonLoot>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst, DungeonLoot::new)));

    protected DungeonLoot(LootItemCondition[] conditionsIn) {
        super(conditionsIn);

    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return LootReg.TD.get();
    }


    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ResourceLocation s = context.getQueriedLootTableId();
        String idSting = String.valueOf(s);
        Entity entity = context.getOptionalParameter(LootContextParams.THIS_ENTITY);

        if (idSting.contains("chests/")) {
            if (idSting.contains("ancient")) {
                AdvancementEvt.addLoot(generatedLoot, entity, 5);
                AdvancementEvt.nightmare_base_reversal_mysteriousLOOT(generatedLoot, entity);

            }
        }
        if (idSting.contains("chests/")){
            if (idSting.contains("dungeon")||idSting.contains("mansion")){
                this.give(generatedLoot,entity,10,"defend_against_runestone",Items.nightmare_base.get(),Items.defend_against_runestone.get());
                this.give(generatedLoot,entity,10,"revive_runestone",Items.nightmare_base.get(),Items.revive_runestone.get());
                this.give(generatedLoot,entity,10,"strengthen_runestone",Items.nightmare_base.get(),Items.strengthen_runestone.get());
            }
        }

        if (idSting.contains("chests/")) {
            if (idSting.contains("dungeon")) {
                AdvancementEvt.nightmare_base_start_pod(generatedLoot, entity);
            }
            if (idSting.contains("mansion")) {
                AdvancementEvt.tricky_puppets(generatedLoot, entity);
            }
        }
        return generatedLoot;
    }
    public void give(ObjectArrayList<ItemStack> generatedLoot,
                                                              Entity entity,
                                                              int lv,String name,
                                                              Item must,
                                                              Item give){
        if (entity instanceof Player player ){
            if (Handler.hascurio(player, must)) {
                CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
                    Map<String, ICurioStacksHandler> curios = handler.getCurios();
                    for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                        ICurioStacksHandler stacksHandler = entry.getValue();
                        IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                        for (int i = 0; i < stacksHandler.getSlots(); i++) {
                            ItemStack stack = stackHandler.getStackInSlot(i);
                            if (stack.is(must)) {
                                if (Mth.nextInt(RandomSource.create(), 1, 100) <= lv) {
                                    if (stack.get(DataReg.tag) != null) {
                                        if (!stack.get(DataReg.tag).getBoolean(name).isPresent()) {
                                            generatedLoot.add(new ItemStack(give));
                                            stack.get(DataReg.tag).putBoolean(name, true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    }
}


