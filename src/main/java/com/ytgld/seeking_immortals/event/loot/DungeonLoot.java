package com.ytgld.seeking_immortals.event.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ytgld.seeking_immortals.event.old.AdvancementEvt;
import com.ytgld.seeking_immortals.init.LootReg;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

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
}


