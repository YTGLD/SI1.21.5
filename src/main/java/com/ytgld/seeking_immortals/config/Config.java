package com.ytgld.seeking_immortals.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class Config {
    public static final ModConfigSpec Common;
    public static final CommonConfig SERVER;
    static {
        ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
        SERVER = new CommonConfig(CLIENT_BUILDER);
        Common = CLIENT_BUILDER.build();
    }
    public static class CommonConfig {
        public ModConfigSpec.IntValue Nightecora ;
        public final  ModConfigSpec.IntValue nightmare_base_redemption_deception ;
        public final ModConfigSpec.DoubleValue nightmare_base_fool_bone ;
        public final ModConfigSpec.IntValue nightmare_base_insight_drug ;
        public final ModConfigSpec.IntValue nightmare_base_insight_drug_2 ;
        public final ModConfigSpec.IntValue nightmare_base_insight_insane ;
        public final ModConfigSpec.IntValue nightmare_base_redemption_deception_time ;
        public final ModConfigSpec.IntValue nightmareBaseMaxItem ;
        public final ModConfigSpec.IntValue eye ;
        public final ModConfigSpec.IntValue immortal ;
        public final ModConfigSpec.DoubleValue disintegrating_stone ;
        public final ModConfigSpec.BooleanValue nightmare_base_black_eye ;
        public final ModConfigSpec.DoubleValue nightmare_base_stone ;
        public final ModConfigSpec.DoubleValue nightmare_base_fool ;
        public final ModConfigSpec.IntValue nightmare_base_insight ;
        public final ModConfigSpec.IntValue nightmare_base_redemption ;
        public final ModConfigSpec.IntValue nightmare_base_reversal ;
        public final ModConfigSpec.IntValue nightmare_base_start ;

        public CommonConfig(ModConfigSpec.Builder BUILDER) {
            BUILDER.push("噩梦");

            nightmareBaseMaxItem = BUILDER
                    .translation("seeking_immortals.config.nightmareBaseMaxItem")
                    .defineInRange("nightmare_", 3, 0, 7);

            Nightecora = BUILDER
                    .translation("seeking_immortals.config.Nightecora")
                    .defineInRange("Nightecora_", 10, 0, 100);
            nightmare_base_redemption_deception = BUILDER
                    .translation("seeking_immortals.config.nightmare_base_redemption_deception")
                    .defineInRange("nightmare_base_redemption_deception", 100, 0, 100);
            nightmare_base_redemption_deception_time = BUILDER
                    .translation("seeking_immortals.config.nightmare_base_redemption_deception_time")
                    .defineInRange("nightmare_base_redemption_deception_time", 7, 0, 100);

            nightmare_base_fool_bone = BUILDER
                    .translation("seeking_immortals.config.nightmare_base_fool_bone")
                    .defineInRange("nightmare_base_fool_bone_", 1.2f, 0, 9999);

            nightmare_base_insight_drug = BUILDER
                    .translation("seeking_immortals.config.nightmare_base_insight_drug")
                    .defineInRange("nightmare_base_insight_drug_", 50, 0, 99999);

            nightmare_base_insight_drug_2 = BUILDER
                    .translation("seeking_immortals.config.nightmare_base_insight_drug_2")
                    .defineInRange("nightmare_base_insight_drug_2", 8, 0, 99999);

            nightmare_base_insight_insane = BUILDER
                    .translation("seeking_immortals.config.nightmare_base_insight_insane")
                    .defineInRange("nightmare_base_insight_insane_", 25, 0, 99999);


            {
                BUILDER.push("无名邪眼");
                eye = BUILDER
                        .translation("seeking_immortals.config.eye")
                        .defineInRange("eye", 10, 1, 10000);
                BUILDER.pop();
                BUILDER.push("不朽轮回之印章");

                immortal = BUILDER
                        .translation("seeking_immortals.config.immortal")
                        .defineInRange("immortal_", 70, 0, 100);

                BUILDER.pop();

                BUILDER.push("裂天石");
                disintegrating_stone = BUILDER
                        .translation("seeking_immortals.config.disintegrating_stone")
                        .defineInRange("disintegrating_stone", 1.3f, 0, 10000);
                BUILDER.pop();

                BUILDER.push("邪念之窥眸");
                nightmare_base_black_eye = BUILDER
                        .translation("seeking_immortals.config.nightmare_base_black_eye")
                        .define("nightmare_base_black_eye", true);
                BUILDER.pop();

                BUILDER.push("死兆方尖碑");
                nightmare_base_stone = BUILDER
                        .translation("seeking_immortals.config.nightmare_base_stone")
                        .defineInRange("nightmare_base_stone", 5f,1,999);
                BUILDER.pop();

                BUILDER.push("愚者之危");
                nightmare_base_fool = BUILDER
                        .translation("seeking_immortals.config.nightmare_base_fool")
                        .defineInRange("nightmare_base_fool", 0.5f,0,1);
                BUILDER.pop();

                BUILDER.push("噩梦洞悉者");
                nightmare_base_insight = BUILDER
                        .translation("seeking_immortals.config.nightmare_base_insight")
                        .defineInRange("nightmare_base_insight", 2,0,1000);
                BUILDER.pop();

                BUILDER.push("“救赎”");
                nightmare_base_redemption = BUILDER
                        .translation("seeking_immortals.config.nightmare_base_redemption")
                        .defineInRange("nightmare_base_redemption", 15,0,100);
                BUILDER.pop();

                BUILDER.push("颠倒之物");
                nightmare_base_reversal = BUILDER
                        .translation("seeking_immortals.config.nightmare_base_reversal")
                        .defineInRange("nightmare_base_reversal", 4,0,100);
                BUILDER.pop();

                BUILDER.push("噩梦之起始");
                nightmare_base_start = BUILDER
                        .translation("seeking_immortals.config.nightmare_base_start")
                        .defineInRange("nightmare_base_start", 100,0,100);
                BUILDER.pop();

            }


            BUILDER.pop();
        }

    }





    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
    }

}
