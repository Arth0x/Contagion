package contagion.content;

import arc.Core;
import arc.func.Cons;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.util.Eachable;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.PointLaserBulletType;
import mindustry.entities.bullet.ShrapnelBulletType;
import mindustry.entities.part.HaloPart;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.*;
import mindustry.entities.units.BuildPlan;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.CacheLayer;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidBridge;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.logic.MessageBlock;
import mindustry.world.blocks.power.Battery;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.SolidPump;
import mindustry.world.blocks.sandbox.ItemSource;
import mindustry.world.blocks.sandbox.LiquidSource;
import mindustry.world.blocks.sandbox.PowerVoid;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.consumers.ConsumeCoolant;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.math.Angles.randLenVectors;
import static mindustry.Vars.*;
import static mindustry.type.ItemStack.with;

public class NHBlocks{

	//Load Mod Factories

	public static Block
    argmot,


    private static void loadTurrets(){

		argmot = new SpeedupTurret("argmot"){{
			shoot = new ShootAlternate(){{
				spread = 7f;
			}};
			
			drawer = new DrawTurret(){{
				parts.add(new RegionPart(){{
					drawRegion = false;
					mirror = true;
					moveY = -2.75f;
					progress = PartProgress.recoil;
					children.add(new RegionPart("-shooter"){{
						heatLayerOffset = 0.001f;
						heatColor = NHColor.thurmixRed.cpy().a(0.85f);
						progress = PartProgress.warmup;
						mirror = outline = true;
						moveX = 2f;
						moveY = 2f;
						moveRot = 11.25f;
					}});
				}}, new RegionPart("-up"){{
					layerOffset = 0.3f;
					
					turretHeatLayer += layerOffset + 0.1f;
					
					heatColor = NHColor.thurmixRed.cpy().a(0.85f);
					outline = false;
				}});
			}};
			
			warmupMaintainTime = 120f;
			
			rotateSpeed = 3f;
			health = 960;
			requirements(Category.turret, with(Items.phaseFabric, 150, NHItems.multipleSteel, 120, NHItems.juniorProcessor, 80, Items.plastanium, 120));
			maxSpeedupScl = 9f;
			speedupPerShoot = 0.3f;
			hasLiquids = true;
			coolant = new ConsumeCoolant(0.15f);
			consumePowerCond(35f, TurretBuild::isActive);
			size = 3;
			range = 200;
			reload = 60f;
			shootCone = 24f;
			shootSound = NHSounds.laser3;
			shootType = new PosLightningType(50f){{
				lightningColor = hitColor = NHColor.lightSkyBack;
				maxRange = rangeOverride = 250f;
				hitEffect = NHFx.hitSpark;
				smokeEffect = Fx.shootBigSmoke2;
			}};
		}};
    }
}