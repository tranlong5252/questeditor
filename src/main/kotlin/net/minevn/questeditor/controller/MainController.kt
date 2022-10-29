package net.minevn.questeditor.controller

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.FileChooser
import net.minevn.questeditor.QuestsEditorApp
import net.minevn.questeditor.utils.Messages
import net.minevn.questeditor.utils.showPopupMessage
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileReader

class MainController {

	@FXML
	private fun onNewFileClick() {
		val fxmlLoader = FXMLLoader(QuestsEditorApp::class.java.getResource("editor.fxml"))
		val scene = Scene(fxmlLoader.load())
		val stage = QuestsEditorApp.stage
		stage.scene = scene
		stage.show()
	}

	@FXML
	private fun onLoadFileClick() {
		val fileChooser = FileChooser()
		fileChooser.extensionFilters.addAll(FileChooser.ExtensionFilter("Quests config file",
			"*.yml", "*.yaml"))
		val file: File? = fileChooser.showOpenDialog(QuestsEditorApp.stage)
		if (file == null) {
			showPopupMessage(Messages.ERR_INVALID_FILE, QuestsEditorApp.stage)
			return
		}
		val yaml: Map<String, Any>  = Yaml().load(FileReader(file))

	}
}