package de.dewarim.goblin

class GlobalConfigService {

    static transactional = true

    String fetchValue(String name) {
        return GlobalConfigEntry.findByName(name)?.entryValue
    }

    String fetchValue(String name, String defaultValue) {
        String configEntry = GlobalConfigEntry.findByName(name)?.entryValue
        return configEntry == null ? defaultValue : configEntry
    }

    Integer fetchValueAsInt(String name){
        try{
            return Integer.parseInt(fetchValue(name))
        }
        catch (Exception ex){
            return 0;
        }
    }

    Integer fetchValueAsInt(String name, Integer defaultValue){
        try{
            return Integer.parseInt(fetchValue(name))
        }
        catch (Exception ex){
            return defaultValue;
        }
    }
}
