package de.dewarim.goblin

/**
 * Configuration Service - this service will hand out global configuration data to any
 * class that needs it.
 *
 * At the moment, it is just returning hardcoded values. Later on, we will load the
 * configuration from config files or from the database.
 *
 * @author ingo
 */
class ConfigService {
	//  TODO: this class is redundant - see GlobalConfigService ... remove this class.
	def configValues = [maxCharactersPerUser:'5']

	String getConfigValue(String name){
		return configValues[name]
	}
}
