package com.ytgld.seeking_immortals.event.now;

import com.ytgld.seeking_immortals.event.CurioDamageEvent;
import com.ytgld.seeking_immortals.item.nightmare.eye;
import net.neoforged.bus.api.SubscribeEvent;

public class EventHandler {
    @SubscribeEvent
    public void CurioLivingIncomingDamageEvent(CurioDamageEvent event){
        eye.CurioDamageEvent((event));
    }
}
