package malbyx.mod.epica.client.FastCart;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import malbyx.mod.epica.FastCart.FastCartEntity;
import malbyx.mod.epica.ModEpica;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.AbstractMinecartRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.entity.state.MinecartRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class FastCartEntityRenderer extends AbstractFastCartEntityRenderer<FastCartEntity, FastCartEntityRenderState> {
    public FastCartEntityRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation) {
        super(context, modelLayerLocation);
    }

    @Override
    public FastCartEntityRenderState createRenderState() {
        return new FastCartEntityRenderState();
    }
}
