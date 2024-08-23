package cz.cuni.gamedev.nail123.roguelike.entities.enemies

import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasSmell
import cz.cuni.gamedev.nail123.roguelike.entities.items.Armor
import cz.cuni.gamedev.nail123.roguelike.entities.items.HealthPotion
import cz.cuni.gamedev.nail123.roguelike.entities.items.Sword
import cz.cuni.gamedev.nail123.roguelike.mechanics.Pathfinding
import cz.cuni.gamedev.nail123.roguelike.mechanics.goBlindlyTowards
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles
import kotlin.random.Random

class Rat(val currentLevel: Int): Enemy(GameTiles.RAT), HasSmell {
    override val blocksMovement = true
    override val blocksVision = false
    override val smellingRadius = 7

    override val maxHitpoints = 10
    override var hitpoints = 6+currentLevel
    override var attack = 3
    override var defense = 0+currentLevel
    // updated rat and give him different HP and defense based on lvl and more items to drop
    override fun update() {
        if (Pathfinding.chebyshev(position, area.player.position) <= smellingRadius) {
            goBlindlyTowards(area.player.position)
        }
    }

    override fun die() {
        super.die()
        // Drop an item ((sword, Health potion, armor))
        val random = Random.Default

        val randomNumber = random.nextInt(3)

        if(randomNumber == 1)
        {
            this.block.entities.add(Sword(2+currentLevel))
        }
        else if(randomNumber == 2)
        {
            this.block.entities.add(HealthPotion(3+currentLevel))
        }else{
            this.block.entities.add(Armor(1+currentLevel))
        }

    }
}