package cz.cuni.gamedev.nail123.roguelike.entities.items

import cz.cuni.gamedev.nail123.roguelike.entities.Player
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasInventory
import cz.cuni.gamedev.nail123.roguelike.tiles.GameTiles

class Armor(val defense: Int): Defense(GameTiles.ARMOR) {
    override fun onEquip(character: HasInventory) {
        if (character is Player) {
            character.defense += defense
        }
    }

    override fun onUnequip(character: HasInventory) {
        if (character is Player) {
            character.defense -= defense
        }
    }

    override fun toString(): String {
        return "Armor($defense)"
    }
}