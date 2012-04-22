package de.dewarim.goblin.filters

/**
 *
 */
class UserFilters {
    def session

    static needsUser = ['addressBook', 'chatterBox', 'goblinOrder', 'mailBox', 'academy',
        'fight', 'guild', 'item', 'playerCharacter', 'quest', 'shop', 'town', 'user'
    ]

    static String userRx
    static {
        needsUser.each{
            userRx = userRx+"|$it"
        }
        userRx = userRx.replaceFirst('|','')
    }

    public static boolean isAjax(request) {
      return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }


    def filters = {
//        userNeeded(controller:userRx, action:'*'){
//            before = {
//                if( session.pc || params.pc){
//                    log.debug("access allowed to ${controllerName}::${actionName}")
//                    return true
//                }
//                else{
//                    log.debug("access denied to ${controllerName}::${actionName}")
//                    if(isAjax(request)){
//                        render(status:503, text:message(code:'error.player_not_found'))
//                    }
//                    else{
//                        redirect(controller: 'portal', action: 'start')
//                    }
//                    return false
//                }
//            }
//        }
    }
}
