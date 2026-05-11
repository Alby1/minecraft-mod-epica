package malbyx.mod.epica.client.FastCart;

import malbyx.mod.epica.ModEpica;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.resources.Identifier;

public class FastCartEntityRenderer extends MinecartRenderer {
    public FastCartEntityRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation) {
        super(context, modelLayerLocation);
    }

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ModEpica.MOD_ID, "textures/entity/fastcart.png");
    //private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/entity/fastcart.png");

    @Override
    public FastCartEntityRenderState createRenderState() {
        return new FastCartEntityRenderState();
    }


    public Identifier getTexture(FastCartEntityRenderState state) {
        return TEXTURE;
    }
}
