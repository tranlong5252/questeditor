package net.minevn.questeditor

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage

class QuestsEditorApp : Application() {
	override fun start(window: Stage) {
		stage = window
		val fxmlLoader = FXMLLoader(QuestsEditorApp::class.java.getResource("editor.fxml"))
		val scene = Scene(fxmlLoader.load())
		window.title = "QuestsEditor"
		window.icons.add(Image("icon.jpg"))
		window.scene = scene
		window.isMaximized = true
		window.resizableProperty().set(true)
		window.show()
	}

	companion object {
		lateinit var stage: Stage
			private set
	}
}

fun main() {
	Application.launch(QuestsEditorApp::class.java)
}