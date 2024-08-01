package cz.cuni.gamedev.nail123.roguelike.entities.items

import cz.cuni.gamedev.nail123.roguelike.entities.MovingEntity
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasCombatStats
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasVision
import cz.cuni.gamedev.nail123.roguelike.events.logMessage
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles

class Dog: MovingEntity(GameTiles.DOG), HasVision, HasCombatStats {
    override val visionRadius = 7
    override val blocksMovement = true
    override val blocksVision = false

    override var maxHitpoints = 10
    override var hitpoints = 10
    override var attack = 7
    override var defense = 1



    override fun die() {
        super.die()
        this.logMessage("Your companion for this floor died")

    }
}