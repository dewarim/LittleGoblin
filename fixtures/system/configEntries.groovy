import de.dewarim.goblin.GlobalConfigEntry
import grails.util.Environment

def serverUrl =   Environment.current.equals(Environment.DEVELOPMENT) ? 'http://127.0.0.1:8080' : 'http://schedim.de'

fixture{
    usernameMinLength(GlobalConfigEntry, name:'username.min.length', entryValue: 3)
    passwordMinLength(GlobalConfigEntry, name:'password.min.length', entryValue: 6)
    myServerUrl(GlobalConfigEntry, name:'server_url', entryValue: serverUrl)    
}