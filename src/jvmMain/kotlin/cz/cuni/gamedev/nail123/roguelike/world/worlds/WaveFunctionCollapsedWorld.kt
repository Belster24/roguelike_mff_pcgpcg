package cz.cuni.gamedev.nail123.roguelike.world.worlds

import cz.cuni.gamedev.nail123.roguelike.GameConfig
import cz.cuni.gamedev.nail123.roguelike.blocks.Floor
import cz.cuni.gamedev.nail123.roguelike.blocks.Wall
import cz.cuni.gamedev.nail123.roguelike.entities.items.Dog
import cz.cuni.gamedev.nail123.roguelike.entities.enemies.Chest
import cz.cuni.gamedev.nail123.roguelike.entities.enemies.Orc
import cz.cuni.gamedev.nail123.roguelike.entities.enemies.Rat
import cz.cuni.gamedev.nail123.roguelike.entities.enemies.Snake
import cz.cuni.gamedev.nail123.roguelike.entities.objects.Stairs
import cz.cuni.gamedev.nail123.roguelike.entities.unplacable.FogOfWar
import cz.cuni.gamedev.nail123.roguelike.mechanics.Pathfinding
import cz.cuni.gamedev.nail123.roguelike.world.Area
import cz.cuni.gamedev.nail123.roguelike.world.builders.wavefunctioncollapse.WFCAreaBuilder
import org.hexworks.zircon.api.data.Position3D
import java.util.*

class WaveFunctionCollapsedWorld: DungeonWorld() {
    override fun buildLevel(floor: Int): Area {
        val area = WFCAreaBuilder(GameConfig.AREA_SIZE).create()

        area.addAtEmptyPosition(
            area.player,
            Position3D.create(0, 0, 0),
            GameConfig.VISIBLE_SIZE
        )

        area.addEntity(FogOfWar(), Position3D.unknown())

        // Add stairs up
        if (floor > 0) area.addEntity(Stairs(false), area.player.position)

        // Add stairs down
        val floodFill = Pathfinding.floodFill(area.player.position, area)
        val maxDistance = floodFill.values.maxOrNull()!!
        val staircasePosition = floodFill.filter { it.value > maxDistance / 2 }.keys.random()
        area.addEntity(Stairs(), staircasePosition)

        ensureConnectivity(area, staircasePosition)
        removeCycles(area)


        //add rats
        repeat(currentLevel + 1) {
            area.addAtEmptyPosition(Rat(currentLevel), Position3D.defaultPosition(), area.size)
        }

        //add orcs
        repeat(currentLevel - 1) {
            area.addAtEmptyPosition(Orc(currentLevel), Position3D.defaultPosition(), area.size)
        }

        //add snake
        repeat(currentLevel-3) {
            area.addAtEmptyPosition(Snake(currentLevel), Position3D.defaultPosition(), area.size)
        }

        //add chests
        repeat(currentLevel) {
            area.addAtEmptyPosition(Chest(currentLevel), Position3D.defaultPosition(), area.size)
        }
       /* if(currentLevel >= 5) {
            area.addAtEmptyPosition(Dog(), Position3D.defaultPosition(), area.size)
        }*/
        return area.build()
    }

    private fun ensureConnectivity(area: WFCAreaBuilder, finishStair: Position3D) {
        val start = area.player.position
        val floodFill = Pathfinding.floodFill(start, area)
        //val aStart = Pathfinding.aStar(start, finishStair, area)
        val allAccessiblePositions = floodFill.keys.toSet()

        //println(area.blocks.keys)


        val unconnected = area.blocks.keys.filter { it !in allAccessiblePositions && isAccessible(area[it]) }
        for (pos in unconnected) {
            createPathToNearestConnected(area, pos, allAccessiblePositions)
        }
    }

    private fun createPathToNearestConnected(area: WFCAreaBuilder, start: Position3D, connected: Set<Position3D>) {
        val queue: Queue<Pair<Position3D, List<Position3D>>> = LinkedList()
        val visited = mutableSetOf<Position3D>()

        queue.add(Pair(start, listOf(start)))
        visited.add(start)

        while (queue.isNotEmpty()) {
            val (current, path) = queue.remove()
            val neighbors = getNeighbors(area, current)

            for (neighbor in neighbors) {
                if (neighbor in connected) {
                    // Create a path
                    for (pos in path) {
                        if (area.blocks[pos] !is Floor) {
                            area.blocks[pos] = Floor() // Replace non-floor block with floor
                        }
                    }
                    return
                }

                if (neighbor !in visited) {
                    visited.add(neighbor)
                    queue.add(Pair(neighbor, path + neighbor))
                }
            }
        }
    }

    private fun getNeighbors(area: WFCAreaBuilder, pos: Position3D): List<Position3D> {
        val (x, y, z) = pos
        return listOf(
            Position3D.create(x - 1, y, z),
            Position3D.create(x + 1, y, z),
            Position3D.create(x, y - 1, z),
            Position3D.create(x, y + 1, z)
        ).filter { it in area.blocks.keys }
    }

    private fun isAccessible(block: Any?): Boolean {
        println(block)

        return (block is Floor) // Assuming Floor and Door are accessible blocks
    }


    private fun removeCycles(area: WFCAreaBuilder) {
        val visited = mutableSetOf<Position3D>()
        val stack = mutableSetOf<Position3D>()
        val parentMap = mutableMapOf<Position3D, Position3D?>()

        for (pos in area.blocks.keys) {
            if (isAccessible(area.blocks[pos]) && pos !in visited) {
                if (dfs(area, pos, visited, stack, parentMap)) {
                    break
                }
            }
        }
    }

    private fun dfs(
        area: WFCAreaBuilder,
        pos: Position3D,
        visited: MutableSet<Position3D>,
        stack: MutableSet<Position3D>,
        parentMap: MutableMap<Position3D, Position3D?>
    ): Boolean {
        visited.add(pos)
        stack.add(pos)

        for (neighbor in getNeighbors(area, pos)) {
            if (isAccessible(area.blocks[neighbor])) {
                if (neighbor !in visited) {
                    parentMap[neighbor] = pos
                    if (dfs(area, neighbor, visited, stack, parentMap)) {
                        return true
                    }
                } else if (neighbor in stack) {
                    // Found a cycle, remove an edge
                    removeEdge(area, pos, neighbor)
                    return true
                }
            }
        }

        stack.remove(pos)
        return false
    }

    private fun removeEdge(area: WFCAreaBuilder, pos1: Position3D, pos2: Position3D) {
        // Remove an edge between pos1 and pos2
        // Simply turning one of the positions into a wall should break the cycle
        area.blocks[pos1] = Wall()
    }

}