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
        public final ModConfigSpec.IntValue nightmare_base_fool_bone ;
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
                    .comment("“”噩梦基座“给玩家的罪孽数量")
                    .defineInRange("nightmare_", 3, 0, 7);

            Nightecora = BUILDER
                    .comment("Nightecora病毒的额外生命值惩罚，单位百分比")
                    .defineInRange("Nightecora_", 10, 0, 100);
            nightmare_base_redemption_deception = BUILDER
                    .comment("“欺骗”恢复的生命值，单位百分比")
                    .defineInRange("nightmare_base_redemption_deception", 100, 0, 100);
            nightmare_base_redemption_deception_time = BUILDER
                    .comment("“欺骗”恢复的生命值，单位秒")
                    .defineInRange("nightmare_base_redemption_deception_time", 7, 0, 100);

            nightmare_base_fool_bone = BUILDER
                    .comment("危险的头骨造成的额外伤害，“2”是两倍")
                    .defineInRange("nightmare_base_fool_bone", 2, 0, 9999);

            nightmare_base_insight_drug = BUILDER
                    .comment("疯狂灵药的最大属性加成，单位百分比")
                    .defineInRange("nightmare_base_insight_drug", 100, 0, 99999);

            nightmare_base_insight_drug_2 = BUILDER
                    .comment("疯狂灵药的单物品计算的属性衰败，单位百分比")
                    .defineInRange("nightmare_base_insight_drug_2", 8, 0, 99999);

            nightmare_base_insight_insane = BUILDER
                    .comment("癫狂之石的杀死生物后获得的伤害加成，单位百分比")
                    .defineInRange("nightmare_base_insight_insane", 150, 0, 99999);


            {
                BUILDER.push("无名邪眼");
                eye = BUILDER
                        .comment("“”无名邪眼“的抽取灵魂的速度，10就是0.5秒，20为1秒，单位i：刻")
                        .defineInRange("eye", 10, 1, 10000);
                BUILDER.pop();
                BUILDER.push("不朽轮回之印章");

                immortal = BUILDER
                        .comment("不朽轮回之印章的反伤概率")
                        .defineInRange("immortal", 80, 0, 100);

                BUILDER.pop();

                BUILDER.push("裂天石");
                disintegrating_stone = BUILDER
                        .comment("裂天石的单个模组给予的数值")
                        .defineInRange("disintegrating_stone", 1.3f, 0, 10000);
                BUILDER.pop();

                BUILDER.push("邪念之窥眸");
                nightmare_base_black_eye = BUILDER
                        .comment("近视效果开关")
                        .define("nightmare_base_black_eye", true);
                BUILDER.pop();

                BUILDER.push("死兆方尖碑");
                nightmare_base_stone = BUILDER
                        .comment("满血的受伤伤害")
                        .defineInRange("nightmare_base_stone", 5f,1,999);
                BUILDER.pop();

                BUILDER.push("愚者之危");
                nightmare_base_fool = BUILDER
                        .comment("最大处罚值，0.5就是50%")
                        .defineInRange("nightmare_base_fool", 0.5f,0,1);
                BUILDER.pop();

                BUILDER.push("噩梦洞悉者");
                nightmare_base_insight = BUILDER
                        .comment("附魔的减少值")
                        .defineInRange("nightmare_base_insight", 2,0,1000);
                BUILDER.pop();

                BUILDER.push("“救赎”");
                nightmare_base_redemption = BUILDER
                        .comment("属性衰败比例")
                        .defineInRange("nightmare_base_redemption", 15,0,100);
                BUILDER.pop();

                BUILDER.push("颠倒之物");
                nightmare_base_reversal = BUILDER
                        .comment("每次死亡降低的最低值")
                        .defineInRange("nightmare_base_reversal", 4,0,100);
                BUILDER.pop();

                BUILDER.push("噩梦之起始");
                nightmare_base_start = BUILDER
                        .comment("护甲值的处罚")
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
