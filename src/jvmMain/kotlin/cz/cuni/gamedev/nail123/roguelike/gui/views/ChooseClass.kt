package cz.cuni.gamedev.nail123.roguelike.gui.views

import cz.cuni.gamedev.nail123.roguelike.entities.Player

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.api.view.base.BaseView


//tryed to add class system
open class ChooseClass(val tileGrid: TileGrid): BaseView(tileGrid, ColorThemes.arc()) {
    open val msg = "Choose class"

    override fun onDock() {
        val header = Components.textBox(30)
                .addHeader(msg)
                .withAlignmentWithin(screen, ComponentAlignment.CENTER)
                .build()
        val warriorButton = Components.button()
                .withAlignmentAround(header, ComponentAlignment.BOTTOM_LEFT)
                .withText("Warrior")
                .withDecorations(
                        ComponentDecorations.box(BoxType.SINGLE),
                        ComponentDecorations.shadow()
                )
                .build()
        val exitButton = Components.button()
                .withAlignmentAround(header, ComponentAlignment.BOTTOM_RIGHT)
                .withText("Quit")
                .withDecorations(
                        ComponentDecorations.box(BoxType.SINGLE),
                        ComponentDecorations.shadow()
                )
                .build()

        exitButton.handleComponentEvents(ComponentEventType.ACTIVATED) {
            // Restart game
            System.exit(0)
            UIEventResponse.processed()
        }

        warriorButton.handleComponentEvents(ComponentEventType.ACTIVATED) {




            // Go to play view
            replaceWith(PlayView(tileGrid))

            UIEventResponse.processed()
        }

        screen.addComponent(header)
        screen.addComponent(warriorButton)
        screen.addComponent(exitButton)
    }
}