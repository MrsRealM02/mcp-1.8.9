package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityHeartFX extends EntityFX
{
    float particleScaleOverTime;

    protected EntityHeartFX(World worldIn, double p_i1211_2_, double p_i1211_4_, double p_i1211_6_, double p_i1211_8_, double p_i1211_10_, double p_i1211_12_)
    {
        this(worldIn, p_i1211_2_, p_i1211_4_, p_i1211_6_, p_i1211_8_, p_i1211_10_, p_i1211_12_, 2.0F);
    }

    protected EntityHeartFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46354_8_, double p_i46354_10_, double p_i46354_12_, float scale)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.009999999776482582D;
        this.motionY *= 0.009999999776482582D;
        this.motionZ *= 0.009999999776482582D;
        this.motionY += 0.1D;
        this.particleScale *= 0.75F;
        this.particleScale *= scale;
        this.particleScaleOverTime = this.particleScale;
        this.particleMaxAge = 16;
        this.noClip = false;
        this.setParticleTextureIndex(80);
    }

    /**
     * Renders the particle
     */
    public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
        float lvt_9_1_ = ((float)this.particleAge + partialTicks) / (float)this.particleMaxAge * 32.0F;
        lvt_9_1_ = MathHelper.clamp_float(lvt_9_1_, 0.0F, 1.0F);
        this.particleScale = this.particleScaleOverTime * lvt_9_1_;
        super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        if (this.posY == this.prevPosY)
        {
            this.motionX *= 1.1D;
            this.motionZ *= 1.1D;
        }

        this.motionX *= 0.8600000143051147D;
        this.motionY *= 0.8600000143051147D;
        this.motionZ *= 0.8600000143051147D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }

    public static class AngryVillagerFactory implements IParticleFactory
    {
        public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
        {
            EntityFX lvt_16_1_ = new EntityHeartFX(worldIn, xCoordIn, yCoordIn + 0.5D, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
            lvt_16_1_.setParticleTextureIndex(81);
            lvt_16_1_.setRBGColorF(1.0F, 1.0F, 1.0F);
            return lvt_16_1_;
        }
    }

    public static class Factory implements IParticleFactory
    {
        public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
        {
            return new EntityHeartFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        }
    }
}