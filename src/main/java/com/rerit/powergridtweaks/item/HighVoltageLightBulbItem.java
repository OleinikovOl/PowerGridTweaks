package com.rerit.powergridtweaks.item;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.patryk3211.powergrid.electricity.base.ThermalBehaviour;
import org.patryk3211.powergrid.electricity.light.bulb.ILightBulb;
import org.patryk3211.powergrid.electricity.light.bulb.LightBulb;

import java.util.EnumMap;


public class HighVoltageLightBulbItem extends LightBulb {
    public HighVoltageLightBulbItem(Item.Properties properties) {
        super(properties);

        this.voltage = 120.0f;
        this.power = 60.0f;

        this.T_max = 1450.0f;

        this.R_max = this.voltage * this.voltage / this.power;
        this.R_min = 90.0f;

        float dissipationFactor = this.power / (this.T_max - ThermalBehaviour.BASE_TEMPERATURE);

        this.thermalProperties = new ILightBulb.Properties(
                dissipationFactor,
                0.005f,
                this.T_max + 400.0f
        );

        this.canBeDyed = false;

        var models = new EnumMap<State, PartialModel>(State.class);
        models.put(State.OFF, PartialModel.of(ResourceLocation.fromNamespaceAndPath("powergrid", "block/lamps/light_bulb")));
        models.put(State.LOW_POWER, PartialModel.of(ResourceLocation.fromNamespaceAndPath("powergrid", "block/lamps/light_bulb_on")));
        models.put(State.ON, PartialModel.of(ResourceLocation.fromNamespaceAndPath("powergrid", "block/lamps/light_bulb_on")));
        models.put(State.BROKEN, PartialModel.of(ResourceLocation.fromNamespaceAndPath("powergrid", "block/lamps/light_bulb_broken")));
        models.put(State.LIGHT, PartialModel.of(ResourceLocation.fromNamespaceAndPath("powergrid", "block/lamps/light_bulb_light")));

        this.modelSupplier = () -> models::get;
    }
}
