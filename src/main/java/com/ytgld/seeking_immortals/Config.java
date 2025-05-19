package com.ytgld.seeking_immortals;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static Config SERVER;
    public static ModConfigSpec fc;

    static {
        final Pair<Config, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Config::new);
        SERVER = specPair.getLeft();
        fc = specPair.getRight();
    }
    public Config(ModConfigSpec.Builder BUILDER){

        {
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
            BUILDER.pop();
        }



        BUILDER.build();
    }


    public   ModConfigSpec.IntValue Nightecora ;


    public   ModConfigSpec.IntValue nightmare_base_redemption_deception ;

    public   ModConfigSpec.IntValue nightmare_base_fool_bone ;
    public   ModConfigSpec.IntValue nightmare_base_insight_drug ;
    public   ModConfigSpec.IntValue nightmare_base_insight_drug_2 ;

    public   ModConfigSpec.IntValue nightmare_base_insight_insane ;
    public   ModConfigSpec.IntValue nightmare_base_redemption_deception_time ;


    public   ModConfigSpec.IntValue nightmareBaseMaxItem ;


    public   ModConfigSpec.DoubleValue x ;
    public   ModConfigSpec.DoubleValue y ;
    public   ModConfigSpec.DoubleValue z ;



    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
    }

}
