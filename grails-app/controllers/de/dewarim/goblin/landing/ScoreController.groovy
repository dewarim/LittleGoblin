package de.dewarim.goblin.landing;


import de.dewarim.goblin.BaseController
import de.dewarim.goblin.HighScore
import grails.plugins.springsecurity.Secured

class ScoreController extends BaseController {
	def session

	/**
	 * The Highscore list
	 */
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def show = {

		return [
		        highscore:HighScore.list(max:5, sort:'xp', order:'desc')
		]
	}
	
}
