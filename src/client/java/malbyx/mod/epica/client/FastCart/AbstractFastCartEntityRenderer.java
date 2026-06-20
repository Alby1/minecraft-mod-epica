package malbyx.mod.epica.client.FastCart;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import malbyx.mod.epica.ModEpica;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.object.cart.MinecartModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.MinecartRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.entity.vehicle.minecart.MinecartBehavior;
import net.minecraft.world.entity.vehicle.minecart.NewMinecartBehavior;
import net.minecraft.world.entity.vehicle.minecart.OldMinecartBehavior;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class AbstractFastCartEntityRenderer<T extends AbstractMinecart, S extends MinecartRenderState> extends EntityRenderer<T, S> {

    private static final Identifier MINECART_LOCATION = Identifier.fromNamespaceAndPath(ModEpica.MOD_ID, "textures/entity/fast_cart.png");
    private static final float DISPLAY_BLOCK_SCALE = 0.75F;
    protected final MinecartModel model;

    protected AbstractFastCartEntityRenderer(EntityRendererProvider.Context context, MinecartModel model) {
        super(context);
        this.shadowRadius = 0.7F;
        this.model = model;
    }

    public AbstractFastCartEntityRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation) {
        super(context);
        this.shadowRadius = 0.7F;
        this.model = new MinecartModel(context.bakeLayer(modelLayerLocation));
    }

    @Override
    public S createRenderState() {
        return null;
    }

    public void submit(S minecartRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        super.submit(minecartRenderState, poseStack, submitNodeCollector, cameraRenderState);
        poseStack.pushPose();
        long l = minecartRenderState.offsetSeed;
        float f = (((float)(l >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float g = (((float)(l >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float h = (((float)(l >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        poseStack.translate(f, g, h);
        if (minecartRenderState.isNewRender) {
            newRender(minecartRenderState, poseStack);
        } else {
            oldRender(minecartRenderState, poseStack);
        }

        float i = minecartRenderState.hurtTime;
        if (i > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin((double)i) * i * minecartRenderState.damageTime / 10.0F * (float)minecartRenderState.hurtDir));
        }

        BlockState blockState = minecartRenderState.displayBlockState;
        if (blockState.getRenderShape() != RenderShape.INVISIBLE) {
            poseStack.pushPose();
            poseStack.scale(0.75F, 0.75F, 0.75F);
            poseStack.translate(-0.5F, (float)(minecartRenderState.displayOffset - 8) / 16.0F, 0.5F);
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
            this.submitMinecartContents(minecartRenderState, blockState, poseStack, submitNodeCollector, minecartRenderState.lightCoords);
            poseStack.popPose();
        }

        poseStack.scale(-1.0F, -1.0F, 1.0F);
        submitNodeCollector.submitModel(this.model, minecartRenderState, poseStack, this.model.renderType(MINECART_LOCATION), minecartRenderState.lightCoords, OverlayTexture.NO_OVERLAY, minecartRenderState.outlineColor, (ModelFeatureRenderer.CrumblingOverlay)null);
        poseStack.popPose();
    }

    private static <S extends MinecartRenderState> void newRender(S minecartRenderState, PoseStack poseStack) {
        poseStack.mulPose(Axis.YP.rotationDegrees(minecartRenderState.yRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-minecartRenderState.xRot));
        poseStack.translate(0.0F, 0.375F, 0.0F);
    }

    private static <S extends MinecartRenderState> void oldRender(S minecartRenderState, PoseStack poseStack) {
        double d = minecartRenderState.x;
        double e = minecartRenderState.y;
        double f = minecartRenderState.z;
        float g = minecartRenderState.xRot;
        float h = minecartRenderState.yRot;
        if (minecartRenderState.posOnRail != null && minecartRenderState.frontPos != null && minecartRenderState.backPos != null) {
            Vec3 vec3 = minecartRenderState.frontPos;
            Vec3 vec32 = minecartRenderState.backPos;
            poseStack.translate(minecartRenderState.posOnRail.x - d, (vec3.y + vec32.y) / (double)2.0F - e, minecartRenderState.posOnRail.z - f);
            Vec3 vec33 = vec32.add(-vec3.x, -vec3.y, -vec3.z);
            if (vec33.length() != (double)0.0F) {
                vec33 = vec33.normalize();
                h = (float)(Math.atan2(vec33.z, vec33.x) * (double)180.0F / Math.PI);
                g = (float)(Math.atan(vec33.y) * (double)73.0F);
            }
        }

        poseStack.translate(0.0F, 0.375F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - h));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-g));
    }

    public void extractRenderState(T abstractMinecart, S minecartRenderState, float f) {
        super.extractRenderState(abstractMinecart, minecartRenderState, f);
        MinecartBehavior var6 = abstractMinecart.getBehavior();
        if (var6 instanceof NewMinecartBehavior newMinecartBehavior) {
            newExtractState(abstractMinecart, newMinecartBehavior, minecartRenderState, f);
            minecartRenderState.isNewRender = true;
        } else {
            var6 = abstractMinecart.getBehavior();
            if (var6 instanceof OldMinecartBehavior oldMinecartBehavior) {
                oldExtractState(abstractMinecart, oldMinecartBehavior, minecartRenderState, f);
                minecartRenderState.isNewRender = false;
            }
        }

        long l = (long)abstractMinecart.getId() * 493286711L;
        minecartRenderState.offsetSeed = l * l * 4392167121L + l * 98761L;
        minecartRenderState.hurtTime = (float)abstractMinecart.getHurtTime() - f;
        minecartRenderState.hurtDir = abstractMinecart.getHurtDir();
        minecartRenderState.damageTime = Math.max(abstractMinecart.getDamage() - f, 0.0F);
        minecartRenderState.displayOffset = abstractMinecart.getDisplayOffset();
        minecartRenderState.displayBlockState = abstractMinecart.getDisplayBlockState();
    }

    private static <T extends AbstractMinecart, S extends MinecartRenderState> void newExtractState(T abstractMinecart, NewMinecartBehavior newMinecartBehavior, S minecartRenderState, float f) {
        if (newMinecartBehavior.cartHasPosRotLerp()) {
            minecartRenderState.renderPos = newMinecartBehavior.getCartLerpPosition(f);
            minecartRenderState.xRot = newMinecartBehavior.getCartLerpXRot(f);
            minecartRenderState.yRot = newMinecartBehavior.getCartLerpYRot(f);
        } else {
            minecartRenderState.renderPos = null;
            minecartRenderState.xRot = abstractMinecart.getXRot();
            minecartRenderState.yRot = abstractMinecart.getYRot();
        }

    }

    private static <T extends AbstractMinecart, S extends MinecartRenderState> void oldExtractState(T abstractMinecart, OldMinecartBehavior oldMinecartBehavior, S minecartRenderState, float f) {
        float g = 0.3F;
        minecartRenderState.xRot = abstractMinecart.getXRot(f);
        minecartRenderState.yRot = abstractMinecart.getYRot(f);
        double d = minecartRenderState.x;
        double e = minecartRenderState.y;
        double h = minecartRenderState.z;
        Vec3 vec3 = oldMinecartBehavior.getPos(d, e, h);
        if (vec3 != null) {
            minecartRenderState.posOnRail = vec3;
            Vec3 vec32 = oldMinecartBehavior.getPosOffs(d, e, h, (double)0.3F);
            Vec3 vec33 = oldMinecartBehavior.getPosOffs(d, e, h, (double)-0.3F);
            minecartRenderState.frontPos = (Vec3) Objects.requireNonNullElse(vec32, vec3);
            minecartRenderState.backPos = (Vec3)Objects.requireNonNullElse(vec33, vec3);
        } else {
            minecartRenderState.posOnRail = null;
            minecartRenderState.frontPos = null;
            minecartRenderState.backPos = null;
        }

    }

    protected void submitMinecartContents(S minecartRenderState, BlockState blockState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i) {
        submitNodeCollector.submitBlock(poseStack, blockState, i, OverlayTexture.NO_OVERLAY, minecartRenderState.outlineColor);
    }

    protected AABB getBoundingBoxForCulling(T abstractMinecart) {
        AABB aABB = super.getBoundingBoxForCulling(abstractMinecart);
        return !abstractMinecart.getDisplayBlockState().isAir() ? aABB.expandTowards((double)0.0F, (double)((float)abstractMinecart.getDisplayOffset() * 0.75F / 16.0F), (double)0.0F) : aABB;
    }

    public Vec3 getRenderOffset(S minecartRenderState) {
        Vec3 vec3 = super.getRenderOffset(minecartRenderState);
        return minecartRenderState.isNewRender && minecartRenderState.renderPos != null ? vec3.add(minecartRenderState.renderPos.x - minecartRenderState.x, minecartRenderState.renderPos.y - minecartRenderState.y, minecartRenderState.renderPos.z - minecartRenderState.z) : vec3;
    }
}
