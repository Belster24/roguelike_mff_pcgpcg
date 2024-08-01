package cz.cuni.gamedev.nail123.roguelike.entities.enemies

import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasSmell
import cz.cuni.gamedev.nail123.roguelike.entities.items.Armor
import cz.cuni.gamedev.nail123.roguelike.entities.items.HealthPotion
import cz.cuni.gamedev.nail123.roguelike.entities.items.Sword
import cz.cuni.gamedev.nail123.roguelike.mechanics.Pathfinding
import cz.cuni.gamedev.nail123.roguelike.mechanics.goBlindlyTowards
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles
import kotlin.random.Random

class Snake(val currentLevel: Int): Enemy(GameTiles.SNAKE), HasSmell {
    override val blocksMovement = true
    override val blocksVision = false
    override val smellingRadius = 7

    override val maxHitpoints = 10
    override var hitpoints = 3+currentLevel
    override var attack = 8+currentLevel
    override var defense = 1+currentLevel

    override fun update() {
        if (Pathfinding.chebyshev(position, area.player.position) <= smellingRadius) {
            goBlindlyTowards(area.player.position)
        }
    }

    override fun die() {
        super.die()
        // Drop an item ((sword))
        val random = Random.Default

        val randomNumber = random.nextInt(3)

        if(randomNumber == 1)
        {
            this.block.entities.add(Sword(2+currentLevel))
        }
        else if(randomNumber == 2)
        {
            this.block.entities.add(HealthPotion(9+currentLevel))
        }else{
            this.block.entities.add(Armor(8+currentLevel))
        }

    }
}