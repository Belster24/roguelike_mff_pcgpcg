package cz.cuni.gamedev.nail123.roguelike.entities.enemies

import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasSmell
import cz.cuni.gamedev.nail123.roguelike.entities.items.Armor
import cz.cuni.gamedev.nail123.roguelike.entities.items.Axe
import cz.cuni.gamedev.nail123.roguelike.entities.items.HealthPotion
import cz.cuni.gamedev.nail123.roguelike.entities.items.Sword
import cz.cuni.gamedev.nail123.roguelike.mechanics.Pathfinding
import cz.cuni.gamedev.nail123.roguelike.mechanics.goBlindlyTowards
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles
import kotlin.random.Random

class Chest(val currentlevel: Int): Enemy(GameTiles.CHEST), HasSmell {
    override val blocksMovement = true
    override val blocksVision = false
    override val smellingRadius = 0

    override val maxHitpoints = 10
    override var hitpoints = 6
    override var attack = 0
    override var defense = 0

    override fun update() {
        if (Pathfinding.chebyshev(position, area.player.position) <= smellingRadius) {
            goBlindlyTowards(area.player.position)
        }
    }

    override fun die() {
        super.die()
        // Drop an item ((sword))
        val random = Random.Default

        val randomNumber = random.nextInt(4)

        if(randomNumber == 1)
        {
            this.block.entities.add(Sword(3+currentlevel))
        }
        else if(randomNumber == 2)
        {
            this.block.entities.add(HealthPotion(6+currentlevel))
        }else if(randomNumber == 3){
            this.block.entities.add(Armor(5+currentlevel))
        }else{
            this.block.entities.add(Axe(6+currentlevel, 3+currentlevel))
        }

    }
}