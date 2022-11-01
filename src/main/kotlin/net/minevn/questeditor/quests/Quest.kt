package net.minevn.questeditor.quests

import net.minevn.questeditor.utils.showPopupMessage
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class Quest(
	val id: String,
	val name: String,
	val desc: List<String>,
	val done: Int,
	val doneMessage: List<String>,
	val category: String,
	val objective: QuestObjective,
	private val conditions: List<Condition>,
	val awards: List<String>,
	val randomAward: Boolean,
	private val timer: QuestTimer?,
	val cooldown: Long,
	/**
	 * Nhóm nhiệm vụ dành đối với nhiệm vụ phải ấn "nhận nhiệm vụ" <br />
	 * Không thể cùng lúc nhận nhiều nhiệm vụ cùng 1 nhóm
	 */
	val group: String? = null,
	/**
	 * = true nếu là nhiệm vụ phải ấn "nhận nhiệm vụ"
	 */
	val mustObtain: Boolean = false,
) {
	private var announceEnable = false
	private var announceEvery: Int = 0
	private var announce: String? = null

	companion object {
		val quests = mutableMapOf<String, Quest>()
		private lateinit var questsByObjective: Map<QuestObjective, List<Quest>>
		private lateinit var questsByCategory: Map<String, List<Quest>>

		/**
		 * Load quests from directory
		 */
		fun load(file: File) {
			if (!file.exists()) return
			showPopupMessage("Loading quests from ${file.name}")

			if (file.extension != "yml") return
			val config = YamlConfiguration.loadConfiguration(file)
			config.getKeys(false).forEach { id ->
				try {
					val q = config.getConfigurationSection(id)!!
					val name = q.getString("name")!!
					val group = q.getString("group", "")
					val mustobtain = q.getBoolean("mustobtain")
					val done = q.getInt("done")
					val cooldown = q.getLong("cooldown")
					val desc = q.getStringList("desc")
					val donemess = q.getStringList("done-message")
					val category = q.getString("category")!!
					val obj = QuestObjective.valueOf(q.getString("objective")!!)

					val conditions = q.getConfigurationSection("conditions")
						?.getKeys(false)
						?.map { condition ->
							val ctype = ConditionType.valueOf(condition)
							val value = q.getString("conditions.$condition")!!
							Condition(ctype, value)
						}
						?: listOf()

					val randomAward = q.getBoolean("random-award")
					val awards = q.getConfigurationSection("awards")
						?.getKeys(false)
						?.map {
							val section = q.getConfigurationSection("awards.$it")!!
							val itemType = section.getString("type")!!
							val itemData = section.getString("data", "")
							val display = section.getString("display")
							"$itemType$itemData$display"
						}
						?: listOf()

					val timerName = q.getString("timer")
					val timer = if (timerName == null) null else QuestTimer.valueOf(timerName)

					val quest = Quest(id, name, desc, done, donemess, category, obj, conditions, awards,
						randomAward, timer, cooldown, group, mustobtain)
					q.getConfigurationSection("announcement")
						?.also {
							quest.announceEnable = it.getBoolean("enabled")
							quest.announceEvery = it.getInt("every")
							quest.announce = it.getString("message")
						}
					quests[id] = quest
				} catch (ex: Exception) {
					showPopupMessage("Can't load quest $id in file ${file.name} \n${ex.printStackTrace()}")
				}
			}

		}

		@JvmStatic
		fun load() {
			questsByObjective = quests.values.groupBy { it.objective }
			questsByCategory = quests.values.groupBy { it.category }
		}

		@JvmStatic
		fun getByObjective(objective: QuestObjective) = questsByObjective[objective]

		@JvmStatic
		fun getByCategory(category: String) = questsByCategory[category]

		@JvmStatic
		fun get(id: String) = quests[id]
	}
}