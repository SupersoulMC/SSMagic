package hell.supersoul.magic.mobs.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import hell.supersoul.magic.Main;
import hell.supersoul.magic.mobs.AttackM;
import hell.supersoul.magic.mobs.MobM;
import hell.supersoul.magic.util.Util;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.exceptions.InvalidMobTypeException;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class IceWitch extends MobM implements Listener {

	Location z1s1 = new Location(Bukkit.getWorld("battle"), -16.5, 116, 276.5);
	Location z1s2 = new Location(Bukkit.getWorld("battle"), 10.5, 112, 279.5);
	Location z1c1 = new Location(Bukkit.getWorld("battle"), 2, 124, 290);
	Location z1c2 = new Location(Bukkit.getWorld("battle"), -11, 114, 299);
	Location z1b1 = new Location(Bukkit.getWorld("battle"), -5, 121, 299);
	Location z1b2 = new Location(Bukkit.getWorld("battle"), -7, 117, 299);
	Location z1l1 = new Location(Bukkit.getWorld("battle"), -5, 115, 291);
	Location z1l2 = new Location(Bukkit.getWorld("battle"), -5, 107, 291);
	Location z2b1 = new Location(Bukkit.getWorld("battle"), -5, 128, 329);
	Location z2b2 = new Location(Bukkit.getWorld("battle"), -7, 125, 329);
	Location z2s1 = new Location(Bukkit.getWorld("battle"), -9.5, 125, 326.5);
	Location z2s2 = new Location(Bukkit.getWorld("battle"), -1.5, 125, 326.5);
	Location z3s1 = new Location(Bukkit.getWorld("battle"), -17.5, 131, 388.5);
	Location z3s2 = new Location(Bukkit.getWorld("battle"), -0.5, 134, 343.5);
	Location z3s3 = new Location(Bukkit.getWorld("battle"), -17.5, 138, 338.5);
	Location z3s4 = new Location(Bukkit.getWorld("battle"), -5.5, 145, 332.5);
	Location z3b1 = new Location(Bukkit.getWorld("battle"), -5, 139, 331);
	Location z3b2 = new Location(Bukkit.getWorld("battle"), -7, 142, 331);
	Location z3l1 = new Location(Bukkit.getWorld("battle"), -6, 143, 332);
	Location z4s1 = new Location(Bukkit.getWorld("battle"), -5.5, 138, 311.5);
	Location z4s2 = new Location(Bukkit.getWorld("battle"), -10.5, 138, 315.5);
	Location z4s3 = new Location(Bukkit.getWorld("battle"), -5.5, 138, 320.5);
	Location z4s4 = new Location(Bukkit.getWorld("battle"), -0.5, 138, 315.5);
	Location z4b1 = new Location(Bukkit.getWorld("battle"), 6, 137, 327);
	Location z4b2 = new Location(Bukkit.getWorld("battle"), -18, 137, 303);
	Location z4s5 = new Location(Bukkit.getWorld("battle"), -7, 149, 314);
	Location z4s6 = new Location(Bukkit.getWorld("battle"), -5.5, 145.1, 307.5);

	public IceWitch(MythicMob mythicMob, Entity entity) {
		super(mythicMob, entity);

		Util.replaceBlock(z4b1, z4b2, Material.AIR, Material.ICE, (byte) 0, false);
		this.getPhasedAttacks().put(0, new ArrayList<>());
		this.getAttackChance().put(0, new ArrayList<>());
		this.getAttackChance().get(0).add(1);
		this.getPhasedAttacks().get(0).add(new AttackM(this, 100) {
			@Override
			public void attack() {
				try {
					MythicMobs.inst().getAPIHelper().spawnMythicMob("IceMinion3", z4s3);
					MythicMobs.inst().getAPIHelper().spawnMythicMob("IceMinion5", z4s2);
					MythicMobs.inst().getAPIHelper().spawnMythicMob("IceMinion5", z4s2);
					MythicMobs.inst().getAPIHelper().spawnMythicMob("IceMinion5", z4s4);
					MythicMobs.inst().getAPIHelper().spawnMythicMob("IceMinion5", z4s4);
				} catch (InvalidMobTypeException e) {
					e.printStackTrace();
				}
				IceWitch.this.say("Come out now my minions!");
			}
		});

		this.getAttackChance().get(0).add(1);
		this.getPhasedAttacks().get(0).add(new AttackM(this, 200) {
			@Override
			public void attack() {
				IceWitch.this.getEntity().teleport(z4s6);
				IceWitch.this.say("Taste my darkness power!!");
				new BukkitRunnable() {
					int id = 0;
					Entity ent = IceWitch.this.getEntity();

					@Override
					public void run() {
						List<Entity> list = ent.getNearbyEntities(30, 30, 30);
						Iterator<Entity> lit = list.listIterator();
						while (lit.hasNext()) {
							if (!(lit.next() instanceof Player))
								lit.remove();
						}
						Player p = (Player) list.get(Util.randomInteger(0, list.size() - 1));
						Location loc = ent.getLocation().clone();
						loc.setDirection(p.getLocation().toVector().subtract(ent.getLocation().toVector()));
						ArrayList<Player> damaged = new ArrayList<>();
						new BukkitRunnable() {
							int id = 0;

							@Override
							public void run() {
								Location l = loc.clone();
								l.add(0, 1.5, 0);
								for (int i = 0; i < 20; i++) {
									float r = (float) Util.randomInteger(-100, 100) / 1000;
									if (i == 0)
										r = 0;
									Packet packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true,
											(float) l.getX() + r, (float) l.getY(), (float) l.getZ() + r, (float) -1,
											(float) 0, (float) 250 / 255, (float) 1, 0);
									for (Player online : Bukkit.getOnlinePlayers()) {
										((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
									}
								}
								Collection<Entity> toDamage = z4s1.getWorld().getNearbyEntities(l, 1, 1, 1);
								for (Entity ent : toDamage) {
									if (!(ent instanceof Player))
										continue;
									if (damaged.contains(ent))
										continue;
									damaged.add((Player) ent);
									((Player) ent).damage(3d);
								}
								id++;
								loc.add(loc.getDirection().multiply(0.3));
								if (id > 70 || ent.isDead()) {
									this.cancel();
								}
							}
						}.runTaskTimer(Main.getInstance(), 0, 2);
						id++;
						if (id > 40) {
							new BukkitRunnable() {
								@Override
								public void run() {
									ent.teleport(z4s1);
								}
							}.runTaskLater(Main.getInstance(), 100);
							this.cancel();
						}
					}
				}.runTaskTimer(Main.getInstance(), 0, 5);
			}
		});

		this.getAttackChance().get(0).add(1);
		this.getPhasedAttacks().get(0).add(new AttackM(this, 140) {
			@Override
			public void attack() {
				IceWitch.this.say("Don't you run away!");
				new BukkitRunnable() {
					int id = 20;

					@Override
					public void run() {
						Location target = Util.randomLocationInRegion(z4b1, z4b2);
						ArrayList<Player> damaged = new ArrayList<>();
						for (int x = 0; x <= 1; x++) {
							for (int z = 0; z <= 1; z++) {
								Location loc = target.clone().add(x, 0, z);
								Material old = loc.getBlock().getType();
								for (Player player : Bukkit.getOnlinePlayers()) {
									player.sendBlockChange(loc, Material.BEACON, (byte) 0);
								}
								new BukkitRunnable() {
									@Override
									public void run() {
										for (int y = 1; y <= 4; y++) {
											Location l = loc.clone().add(0, y, 0);
											Material o = l.getBlock().getType();
											for (Player player : Bukkit.getOnlinePlayers()) {
												player.sendBlockChange(l, Material.REDSTONE_BLOCK, (byte) 0);
												if (y == 4)
													player.getWorld().playEffect(l, Effect.STEP_SOUND,
															Material.REDSTONE_BLOCK);
												if (player.getWorld().getNearbyEntities(l, 0.5, 0.5, 0.5)
														.contains(player) && !damaged.contains(player)) {
													damaged.add(player);
													player.damage(9);
												}
											}
											new BukkitRunnable() {
												@Override
												public void run() {
													for (Player player : Bukkit.getOnlinePlayers()) {
														player.sendBlockChange(l, o, (byte) 0);
														player.sendBlockChange(loc, old, (byte) 0);
													}
												}
											}.runTaskLater(Main.getInstance(), 20);
										}
									}
								}.runTaskLater(Main.getInstance(), 30);
							}
						}
						id--;
						if (id < 0 || entity.isDead())
							this.cancel();
					}
				}.runTaskTimer(Main.getInstance(), 0, 5);
			}
		});

		this.getAttackChance().get(0).add(1);
		this.getPhasedAttacks().get(0).add(new AttackM(this, 100) {
			@Override
			public void attack() {
				IceWitch.this.say("Did you really think you can win? Take THIS!");
				new BukkitRunnable() {
					@Override
					public void run() {
						IceWitch.this
								.say("Run up to the sides or to the back!! Elsa's dropping an Ice Bomb! Be careful!!!");
						new BukkitRunnable() {
							@Override
							public void run() {

								for (int x = 0; x <= 2; x++) {
									for (int y = 0; y <= 2; y++) {
										for (int z = 0; z <= 2; z++) {
											Location spawn = z4s5.clone().add(x, y, z);
											z4s5.getWorld().spawnFallingBlock(spawn, Material.TNT, (byte) 0);
											ArrayList<Player> damaged = new ArrayList<>();
											new BukkitRunnable() {
												int id = 7;

												@Override
												public void run() {
													z4s5.getWorld().spigot().playEffect(spawn, Effect.CLOUD);
													z4s5.getWorld().playEffect(spawn, Effect.STEP_SOUND,
															Material.PACKED_ICE);
													id--;
													if (id < 0) {
														Location corner1 = z4b1.clone().add(0, 1, 0);
														Location corner2 = z4b2.clone().add(0, 1, 0);
														int minX = Math.min(corner1.getBlockX(), corner2.getBlockX());
														int maxX = Math.max(corner1.getBlockX(), corner2.getBlockX());
														int minZ = Math.min(corner1.getBlockZ(), corner2.getBlockZ());
														int maxZ = Math.max(corner1.getBlockZ(), corner2.getBlockZ());
														int LY = corner1.getBlockY();
														int counter = 0;
														for (int x2 = minX; x2 <= maxX; x2++) {
															for (int z2 = minZ; z2 <= maxZ; z2++) {
																Location loc = new Location(z4b1.getWorld(), x2, LY,
																		z2);
																counter++;
																if (counter == 7) {
																	z4s5.getWorld().spigot().playEffect(loc,
																			Effect.CLOUD);
																	z4s5.getWorld().playEffect(loc, Effect.STEP_SOUND,
																			Material.PACKED_ICE);
																	counter = 0;
																}
																for (Entity ent : z4s5.getWorld().getNearbyEntities(loc,
																		0.5, 0.5, 0.5)) {
																	if (!(ent instanceof Player))
																		continue;
																	Player player = (Player) ent;
																	if (player.getLocation().getBlockY() > LY)
																		continue;
																	if (damaged.contains(player))
																		continue;
																	damaged.add(player);
																	player.damage(16d);
																}
															}
														}
														this.cancel();
													}
												}
											}.runTaskTimer(Main.getInstance(), 0, 4);
										}
									}
								}
							}
						}.runTaskLater(Main.getInstance(), 40);
					}
				}.runTaskLater(Main.getInstance(), 40);
			}
		});

		this.getPhasedAttacks().put(1, new ArrayList<>());
		this.getAttackChance().put(1, new ArrayList<>());
		this.getAttackChance().get(1).add(1);
		this.getPhasedAttacks().get(1).add(new AttackM(this, 100) {
			@Override
			public void attack() {
				try {
					MythicMobs.inst().getAPIHelper().spawnMythicMob("IceMinion2", z4s3);
					MythicMobs.inst().getAPIHelper().spawnMythicMob("IceMinion4", z4s2);
					MythicMobs.inst().getAPIHelper().spawnMythicMob("IceMinion4", z4s2);
					MythicMobs.inst().getAPIHelper().spawnMythicMob("IceMinion4", z4s4);
					MythicMobs.inst().getAPIHelper().spawnMythicMob("IceMinion4", z4s4);
				} catch (InvalidMobTypeException e) {
					e.printStackTrace();
				}
				IceWitch.this.say("GO, KILL THEM ALL!!!");
			}
		});

		this.getAttackChance().get(1).add(1);
		this.getPhasedAttacks().get(1).add(new AttackM(this, 200) {
			@Override
			public void attack() {
				IceWitch.this.say("This will be your FINAL MOMENT!!!");
				IceWitch.this.getEntity().teleport(z4s6);
				new BukkitRunnable() {
					int id = 0;
					Entity ent = IceWitch.this.getEntity();

					@Override
					public void run() {
						List<Entity> list = ent.getNearbyEntities(30, 30, 30);
						Iterator<Entity> lit = list.listIterator();
						while (lit.hasNext()) {
							if (!(lit.next() instanceof Player))
								lit.remove();
						}
						Player p = (Player) list.get(Util.randomInteger(0, list.size() - 1));
						Location loc = IceWitch.this.getEntity().getLocation().clone();
						loc.setDirection(p.getLocation().toVector().subtract(ent.getLocation().toVector()));
						ArrayList<Player> damaged = new ArrayList<>();
						new BukkitRunnable() {
							int id = 0;

							@Override
							public void run() {
								Location l = loc.clone();
								l.add(0, 1.5, 0);
								for (int i = 0; i < 20; i++) {
									float r = (float) Util.randomInteger(-100, 100) / 800;
									if (i == 0)
										r = 0;
									Packet packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true,
											(float) l.getX() + r, (float) l.getY(), (float) l.getZ() + r, (float) 0.7,
											(float) 0, (float) 0, (float) 1, 0);
									for (Player online : Bukkit.getOnlinePlayers()) {
										((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
									}
								}
								Collection<Entity> toDamage = z4s1.getWorld().getNearbyEntities(l, 1, 1, 1);
								for (Entity ent : toDamage) {
									if (!(ent instanceof Player))
										continue;
									if (damaged.contains(ent))
										continue;
									damaged.add((Player) ent);
									((Player) ent).damage(3d);
								}
								id++;
								loc.add(loc.getDirection().multiply(0.4));
								if (id > 100 || entity.isDead()) {
									this.cancel();
								}
							}
						}.runTaskTimer(Main.getInstance(), 0, 1);
						id++;
						if (id > 40) {
							new BukkitRunnable() {
								@Override
								public void run() {
									ent.teleport(z4s1);
								}
							}.runTaskLater(Main.getInstance(), 100);
							this.cancel();
						}
					}
				}.runTaskTimer(Main.getInstance(), 0, 4);
			}
		});

		this.getAttackChance().get(1).add(1);
		this.getPhasedAttacks().get(1).add(new AttackM(this, 100) {
			@Override
			public void attack() {
				IceWitch.this.say("ArghhhHGHGHHGHHGGHH!!!!");
				new BukkitRunnable() {
					int c = 7;
					Entity ent = IceWitch.this.getEntity();

					@Override
					public void run() {
						List<Entity> list = ent.getNearbyEntities(30, 30, 30);
						Iterator<Entity> lit = list.listIterator();
						while (lit.hasNext()) {
							if (!(lit.next() instanceof Player))
								lit.remove();
						}
						Player p = (Player) list.get(Util.randomInteger(0, list.size() - 1));
						ArrayList<Location> area = new ArrayList<>();
						Location loc = ent.getLocation().clone();
						ArrayList<Player> damaged = new ArrayList<>();
						loc.setDirection(p.getLocation().toVector().subtract(ent.getLocation().toVector()));
						for (int j = 0; j <= 40; j++) {
							Location l = loc.clone();
							area.add(l);
							l.add(0, 1.5, 0);
							for (int i = 0; i < 10; i++) {
								float r = (float) Util.randomInteger(-100, 100) / 1000;
								if (i == 0)
									r = 0;
								Packet packet = new PacketPlayOutWorldParticles(EnumParticle.CLOUD, true,
										(float) l.getX() + r, (float) l.getY(), (float) l.getZ() + r, (float) 0,
										(float) 0, (float) 0, (float) 1, 0);
								for (Player online : Bukkit.getOnlinePlayers()) {
									((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
								}
							}
							loc.add(loc.getDirection().multiply(0.7));
						}
						new BukkitRunnable() {
							int id = 8;

							@Override
							public void run() {
								for (Location loc : area) {
									for (int i = 0; i < 40; i++) {
										float r = (float) Util.randomInteger(-100, 100) / 1000;
										if (i == 0)
											r = 0;
										Packet packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true,
												(float) loc.getX() + r, (float) loc.getY(), (float) loc.getZ() + r,
												(float) 0.4, (float) 0, (float) 0.5, (float) 1, 0);
										for (Player online : Bukkit.getOnlinePlayers()) {
											((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
										}
									}
									Collection<Entity> toDamage = z4s1.getWorld().getNearbyEntities(loc, 1, 1, 1);
									for (Entity ent : toDamage) {
										if (!(ent instanceof Player))
											continue;
										if (damaged.contains(ent))
											continue;
										damaged.add((Player) ent);
										((Player) ent).damage(4d);
									}
								}
								id--;
								if (id < 0 || entity.isDead())
									this.cancel();
							}
						}.runTaskTimer(Main.getInstance(), 30, 4);
						c--;

						if (c < 0 || entity.isDead())
							this.cancel();
					}
				}.runTaskTimer(Main.getInstance(), 0, 20);
			}
		});

		this.getAttackChance().get(1).add(1);
		this.getPhasedAttacks().get(1).add(new AttackM(this, 100) {
			@Override
			public void attack() {
				IceWitch.this.say("GET OOOUUUUUTTT!!!");
				Location corner1 = z4b1.clone().add(0, 15, 0);
				Location corner2 = z4b2.clone().add(0, 15, 0);
				new BukkitRunnable() {
					int id = 40;

					@Override
					public void run() {
						Location spawn = Util.randomLocationInRegion(corner1, corner2);
						FallingBlock fb = spawn.getWorld().spawnFallingBlock(spawn, Material.PACKED_ICE, (byte) 0);
						fb.setDropItem(false);
						id--;
						if (id < 0 || entity.isDead())
							this.cancel();
					}
				}.runTaskTimer(Main.getInstance(), 0, 3);
			}
		});
	}

	@Override
	public void onSpawn() {
		this.startAttacking();
	}

	@Override
	public void changeAttackPhase(int oldPhase, int newPhase) {
		super.changeAttackPhase(oldPhase, newPhase);
		if (newPhase == 1) {
			this.say("Won't you just DIE ALREADY!!!");
			new BukkitRunnable() {
				@Override
				public void run() {
					IceWitch.this.say("Stay away from the Ice Floor! It's breaking!!!");
				}
			}.runTaskLater(Main.getInstance(), 40);
			new BukkitRunnable() {
				int id = 7;
				Location corner1 = z4b1.clone();
				Location corner2 = z4b2.clone();
				int minX = Math.min(corner1.getBlockX(), corner2.getBlockX());
				int maxX = Math.max(corner1.getBlockX(), corner2.getBlockX());
				int minZ = Math.min(corner1.getBlockZ(), corner2.getBlockZ());
				int maxZ = Math.max(corner1.getBlockZ(), corner2.getBlockZ());
				int LY = corner1.getBlockY();

				@Override
				public void run() {
					int counter = 0;
					for (int x2 = minX; x2 <= maxX; x2++) {
						for (int z2 = minZ; z2 <= maxZ; z2++) {
							Location loc = new Location(z4b1.getWorld(), x2, LY, z2);
							if (loc.getBlock().getType().equals(Material.ICE)) {
								counter++;
								if (counter == 3) {
									z4s5.getWorld().spigot().playEffect(loc, Effect.CLOUD);
									z4s5.getWorld().playEffect(loc, Effect.STEP_SOUND, Material.PACKED_ICE);
									counter = 0;
								}
							}
						}
					}
					id--;
					if (id < 0) {
						IceWitch.this.say("I'M GONNA KILL YOU ALL!!!!");
						IceWitch.this.nextAttack(1);
						Util.replaceBlock(corner1, corner2, Material.ICE, Material.AIR, (byte) 0, false);
						this.cancel();
					}
				}
			}.runTaskTimer(Main.getInstance(), 40, 10);
		}
	}

	@EventHandler
	public void onFallingBlockLand(EntityChangeBlockEvent event) {
		if (event.getEntityType().equals(EntityType.FALLING_BLOCK)
				&& event.getBlock().getWorld().equals(this.getEntity().getWorld())) {
			FallingBlock fb = (FallingBlock) event.getEntity();
			fb.setDropItem(false);
			if (fb.getBlockId() == 174) {
				Location loc = event.getBlock().getLocation();
				event.getBlock().getWorld().createExplosion(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 3, false,
						false);
			}
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamage(EntityDamageEvent event) {
		
		if (!event.getEntity().equals(this.getEntity()))
			return;
		if (event.isCancelled())
			return;
		if (!(event.getEntity() instanceof LivingEntity))
			return;
		
		LivingEntity ent = (LivingEntity) event.getEntity();
		double previousHealth = ent.getHealth();
		double newHealth = ent.getHealth() - event.getFinalDamage();
		Bukkit.getLogger().info(this.getMythicMob().getHealth() + "");
		if (previousHealth > this.getMythicMob().getHealth() / 2 && newHealth <= this.getMythicMob().getHealth() / 2)
			this.changeAttackPhase(0, 1);
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub

	}

}
