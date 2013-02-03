package de.dewarim.goblin.ticks

import org.springframework.beans.factory.NoSuchBeanDefinitionException

class Tick {
    
    transient  def grailsApplication
    
    static constraints = {
        name unique: true, blank: false
        beanName blank: false, validator: {val, obj ->
            try{ 
                def bean = obj.grailsApplication.mainContext.getBean(val)
                if (bean instanceof ITickListener){
                    return true
                }
                return 'not.listener'
            }catch (NoSuchBeanDefinitionException e){
                return 'bean.missing'
            }
        }
        tickLength validator: {val ->
            return val < 1000 ? 'too.small' : true
        }
    }
    
    String name
    
    /**
     * Time between two ticks in milliseconds
     */
    Long tickLength = 60000
    
    String beanName
    Long currentTick = 0
    Boolean active = true

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Tick)) return false

        Tick tick = (Tick) o

        if (active != tick.active) return false
        if (currentTick != tick.currentTick) return false
        if (name != tick.name) return false
        if (beanName != tick.beanName) return false
        if (tickLength != tick.tickLength) return false

        return true
    }

    int hashCode() {
        return (name != null ? name.hashCode() : 0)
    }
    
    Object fetchListener(){
        return domainClass.grailsApplication.getMainContext().getBean(beanName)
    }
}
