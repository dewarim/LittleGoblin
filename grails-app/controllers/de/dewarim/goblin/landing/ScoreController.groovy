package de.dewarim.goblin.landing

import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.BaseController
import de.dewarim.goblin.HighScore

class ScoreController extends BaseController {

	/**
	 * The Highscore list
	 */
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def show() {

		return [
		        highscore:HighScore.list(max:5, sort:'xp', order:'desc')
		]
	}
}
