package cz.cuni.gamedev.nail123.roguelike.entities.items

import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasInventory
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.Inventory
import org.hexworks.zircon.api.data.Tile

abstract class Defense(tile: Tile): Item(tile) {
    override fun isEquipable(character: HasInventory): Inventory.EquipResult {
        return if (character.inventory.equipped.filterIsInstance<Defense>().isNotEmpty()) {
            Inventory.EquipResult(false, "Cannot equip two armors")
        } else Inventory.EquipResult.Success
    }
}