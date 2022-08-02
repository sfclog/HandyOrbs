package me.qKing12.HandyOrbs.NMS;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Bisected;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.Particles;
import net.minecraft.network.protocol.game.PacketPlayOutWorldParticles;
import net.minecraft.world.entity.projectile.EntityFishingHook;
import xyz.xenondevs.particle.ParticleEffect;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class NMS_v1_18_R2 implements NMS{
    Field fishCatchTime=null;

    Material grass=Material.GRASS_BLOCK;
    Material sugar_cane=Material.SUGAR_CANE;
    Material crops=Material.WHEAT;
    Material carrots=Material.CARROTS;
    Material potatoes=Material.POTATOES;
    Material beetroot = Material.BEETROOTS;
    Material water=Material.WATER;
    Material nether_warts=Material.NETHER_WART;
    Material soil=Material.FARMLAND;

    public Material getGrass(){
        return grass;
    }

    public Material getSugarCane(){
        return sugar_cane;
    }

    public Material getCrops(){
        return crops;
    }

    public Material getCarrots() { return carrots; }

    public Material getPotatoes() { return potatoes; }

    public Material getBeetroot() { return beetroot; }

    public Material getWater(){
        return water;
    }

    public Material getNetherWarts(){
        return nether_warts;
    }

    public Material getSoil(){
        return soil;
    }

    public void treeSaver(ArrayList<BlockState> blocks, Location loc){
        Block b = loc.getBlock();
        if(b.getType().toString().contains("WOOD") || b.getType().toString().contains("LOG") || b.getType().toString().contains("LEAVES")){
            BlockState bState = b.getState();
            bState.update();
            blocks.add(bState);
            loc.getBlock().setType(Material.AIR);
            treeSaver(blocks, loc.clone().add(1, 0, 0));
            treeSaver(blocks, loc.clone().add(-1, 0, 0));
            treeSaver(blocks, loc.clone().add(0, 1, 0));
            treeSaver(blocks, loc.clone().add(0, -1, 0));
            treeSaver(blocks, loc.clone().add(0, 0, 1));
            treeSaver(blocks, loc.clone().add(0, 0, -1));
        }
    }


    @Override
    public void sendParticle(String particles, boolean b, float x, float y, float z, float offset1, float offset2, float offset3, int velocity, int cantitate, Location loc){

        if(particles.equalsIgnoreCase("FIREWORKS_SPARK"))

            ParticleEffect.FIREWORKS_SPARK.display(new Location(loc.getWorld(),x,y,z));

        else if(particles.equalsIgnoreCase("VILLAGER_HAPPY"))

            ParticleEffect.VILLAGER_HAPPY.display(new Location(loc.getWorld(),x,y,z));

        else if(particles.equalsIgnoreCase("VILLAGER_ANGRY"))

            ParticleEffect.VILLAGER_ANGRY.display(new Location(loc.getWorld(),x,y,z));

        else

            ParticleEffect.FLAME.display(new Location(loc.getWorld(),x,y,z));
        





    }

    

    @SuppressWarnings("deprecation")
	@Override
    public void changeBlockData(Block b, byte nr){
        try {
            BlockState blockState = b.getState();
            blockState.setRawData(nr);
            blockState.update(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeBlockData(Block b, byte nr, boolean type){
        Bisected data = (Bisected) b.getBlockData();
        if(nr==(byte)0)
            data.setHalf(Bisected.Half.BOTTOM);
        else
            data.setHalf(Bisected.Half.TOP);

        b.setBlockData(data);
    }

 
    @Override
    public void setBiteTime(FishHook hook, int time){
        EntityFishingHook hookCopy = (EntityFishingHook) ((CraftEntity) hook).getHandle();

        if(fishCatchTime==null) {
            try {
                fishCatchTime = EntityFishingHook.class.getDeclaredField("ah");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        fishCatchTime.setAccessible(true);

        try {
            fishCatchTime.setInt(hookCopy, time);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        fishCatchTime.setAccessible(false);
    }
}
