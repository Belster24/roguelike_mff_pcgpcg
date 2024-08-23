package cz.cuni.gamedev.nail123.roguelike.entities.items

import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasInventory
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles


//more power less hp
class Axe(val attackPower: Int, val hitPoints: Int): Weapon(GameTiles.AXE) {
    override fun onEquip(character: HasInventory) {
        if (character is Player) {
            character.attack += attackPower
            character.hitpoints-=hitPoints
        }
    }

    override fun onUnequip(character: HasInventory) {
        if (character is Player) {
            character.attack -= attackPower
            character.hitpoints+=hitPoints
        }
    }

    override fun toString(): String {
        return "Axe(+$attackPower power, -$hitPoints health)"
    }
}