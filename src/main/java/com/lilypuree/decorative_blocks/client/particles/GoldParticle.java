package com.lilypuree.decorative_blocks.client.particles;

import net.minecraft.client.particle.*;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GoldParticle extends SpriteTexturedParticle {

    private GoldParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double speedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, speedIn);
        float f = this.rand.nextFloat() * 0.1F + 0.2F;
        this.particleRed = f;
        this.particleGreen = f;
        this.particleBlue = f;
        this.setSize(0.02F, 0.02F);
        this.particleScale *= this.rand.nextFloat() * 0.6F + 0.5F;
        this.motionX = 0;
        this.motionZ = 0;
        this.motionY *= 0.02;
        this.maxAge = (int)(30.0D / (Math.random() * 0.8D + 0.2D));
    }

    @Override
    protected int getBrightnessForRender(float partialTick) {
        BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
        return this.world.isBlockLoaded(blockpos) ? this.world.getCombinedLight(blockpos, 5) : 0;
    }

    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
        this.resetPositionToBB();
    }

    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.maxAge-- <= 0 || this.particleAlpha <= 0.1F) {
            this.setExpired();
        } else {
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.99D;
            this.motionY *= 0.99D;
            this.motionZ *= 0.99D;
            this.width *= 0.9D;
            this.height *= 0.9D;
            if(this.maxAge > 30) {
                this.particleAlpha *= MathHelper.clamp(particleAlpha, 0.97D, 1.0D) * 0.98;
            }
        }
    }



    @OnlyIn(Dist.CLIENT)
    public static class GoldFactory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public GoldFactory(IAnimatedSprite spriteSetIn) {
            this.spriteSet = spriteSetIn;
        }

        public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            GoldParticle goldParticle = new GoldParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            float random = worldIn.rand.nextFloat();
            if(random < 0.25F){
                goldParticle.setColor((float)0xFD/0xFF , (float)0xF5/0xFF, (float)0x5F/0xFF);
            }else if(random <0.7F){
                goldParticle.setColor((float)0xFF/0xFF , (float)0xFD/0xFF, (float)0xE0/0xFF);
            }else if(random <0.85F){
                goldParticle.setColor((float)0xFA/0xFF , (float)0xD6/0xFF, (float)0x4A/0xFF);
            }else{
                goldParticle.setColor((float)0xE9/0xFF , (float)0xB1/0xFF, (float)0x15/0xFF);
            }

            goldParticle.selectSpriteRandomly(spriteSet);
            goldParticle.setAlphaF(1.0F - worldIn.rand.nextFloat() * 0.1F);
            return goldParticle;
        }
    }
}
