package net.minevn.questeditor

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage

class QuestsEditorApp : Application() {
	override fun start(stage: Stage) {
		QuestsEditorApp.stage = stage
		val fxmlLoader = FXMLLoader(QuestsEditorApp::class.java.getResource("main.fxml"))
		val scene = Scene(fxmlLoader.load())
		stage.title = "QuestsEditor"
		stage.icons.add(Image("icon.jpg"))
		stage.scene = scene
		stage.show()
	}

	companion object {
		lateinit var stage: Stage
			private set
	}
}

fun main() {
	Application.launch(QuestsEditorApp::class.java)
}