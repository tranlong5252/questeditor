package net.minevn.questeditor.utils

import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.stage.Popup
import javafx.stage.Stage
import net.minevn.questeditor.QuestsEditorApp

fun createPopup(message: String?): Popup {
	val popup = Popup()
	popup.isAutoFix = true
	popup.isAutoHide = true
	popup.isHideOnEscape = true
	val label = Label(message)
	label.onMouseReleased = EventHandler { popup.hide() }
	label.stylesheets.add("/css/styles.css")
	label.styleClass.add("popup")
	popup.content.add(label)
	return popup
}

fun showPopupMessage(message: String?, stage: Stage = QuestsEditorApp.stage) {
	val popup: Popup = createPopup(message)
	popup.onShown = EventHandler {
		popup.x = stage.x + stage.width / 2 - popup.width / 2
		popup.y = stage.y + stage.height / 2 - popup.height / 2
	}
	popup.show(stage)
}