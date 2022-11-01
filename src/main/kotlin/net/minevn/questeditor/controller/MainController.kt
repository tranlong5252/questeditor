package net.minevn.questeditor.controller

import javafx.fxml.FXML
import javafx.scene.control.Tab
import javafx.scene.control.TextArea
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.stage.FileChooser
import net.minevn.questeditor.QuestsEditorApp
import net.minevn.questeditor.quests.Quest
import net.minevn.questeditor.utils.Messages
import net.minevn.questeditor.utils.showPopupMessage
import java.io.File

class MainController {

	lateinit var lore: TextArea
	private var file: File? = null
	private val window = QuestsEditorApp.stage
	lateinit var addQuest: Tab
	lateinit var treeView: TreeView<Quest>
	private val questsCache: List<Tab> = mutableListOf()

	@FXML
	private fun loadFile() {
		val fileChooser = FileChooser()
		fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("Quests config file", "*.yml", "*.yaml"))
		file = fileChooser.showOpenDialog(QuestsEditorApp.stage)
		if (file == null) {
			showPopupMessage(Messages.ERR_INVALID_FILE)
			return
		}
		loadQuests(file!!)
	}

	private fun loadQuests(file: File) {
		Quest.load(file)
		val ti: TreeItem<Quest> = TreeItem(Quest.quests.values.toList()[0])
		treeView.isEditable = true
		treeView.root = ti
	}

	@FXML
	fun close() {
		if (questsCache.isEmpty()) window.close()
	}

	@FXML
	fun addPrompt() {
		//TODO add quest tab
	}

	@FXML
	fun saveFile() {
		//TODO
	}

	fun addQuest() {

	}
}