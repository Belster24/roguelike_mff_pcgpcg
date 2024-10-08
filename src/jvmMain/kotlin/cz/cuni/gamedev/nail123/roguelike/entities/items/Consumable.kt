package cz.cuni.gamedev.nail123.roguelike.entities.items

import cz.cuni.gamedev.nail123.roguelike.entities.attributes.HasInventory
import cz.cuni.gamedev.nail123.roguelike.entities.attributes.Inventory
import org.hexworks.zircon.api.data.Tile

abstract class Consumable(tile: Tile): Item(tile) {
    override fun isEquipable(character: HasInventory): Inventory.EquipResult {
    //added consumable that can be stucked to boost some stat
        return Inventory.EquipResult.Success
    }
}