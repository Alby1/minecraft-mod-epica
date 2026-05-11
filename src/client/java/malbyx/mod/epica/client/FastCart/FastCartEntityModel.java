package malbyx.mod.epica.client.FastCart;

import malbyx.mod.epica.FastCart.FastCartEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.object.cart.MinecartModel;

public class FastCartEntityModel<T extends FastCartEntity> extends MinecartModel {
    public FastCartEntityModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        return LayerDefinition.create(modelData, 64, 32);
    }
}
