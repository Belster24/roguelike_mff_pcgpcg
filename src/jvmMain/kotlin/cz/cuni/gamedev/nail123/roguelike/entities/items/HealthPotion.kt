package cz.cuni.gamedev.nail123.roguelike.entities.items

import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasInventory
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles

class HealthPotion(val healthPower: Int): Consumable(GameTiles.HEALTH_P) {
    override fun onEquip(character: HasInventory) {
        if (character is Player) {
            character.hitpoints += healthPower
        }
    }

    override fun onUnequip(character: HasInventory) {
        if (character is Player) {
            //character.hitpoints -= healthPower
        }
    }

    override fun toString(): String {
        return "HealthPotion($healthPower)"
    }
}