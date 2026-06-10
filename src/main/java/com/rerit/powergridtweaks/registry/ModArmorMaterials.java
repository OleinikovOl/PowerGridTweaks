package com.rerit.powergridtweaks.registry;

import com.rerit.powergridtweaks.PowerGridTweaks;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;

import java.util.List;

public class ModArmorMaterials {
    public static final Holder<ArmorMaterial> MINER_HELMET =
            withLayer(ArmorMaterials.IRON, ResourceLocation.fromNamespaceAndPath(PowerGridTweaks.MOD_ID, "miner_helmet"));

    private ModArmorMaterials() {
    }

    private static Holder<ArmorMaterial> withLayer(Holder<ArmorMaterial> base, ResourceLocation layerName) {
        ArmorMaterial material = base.value();
        return Holder.direct(new ArmorMaterial(
                material.defense(),
                material.enchantmentValue(),
                material.equipSound(),
                material.repairIngredient(),
                List.of(new ArmorMaterial.Layer(layerName)),
                material.toughness(),
                material.knockbackResistance()
        ));
    }
}
