<%@ page import="de.dewarim.goblin.ticks.Tick" %>



<div class="fieldcontain ${hasErrors(bean: tick, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="name" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${tick?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tick, field: 'beanName', 'error')} required">
	<label for="beanName">
		<g:message code="tick.beanName.label" default="Bean Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="beanName" required="" value="${tick?.beanName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tick, field: 'tickLength', 'error')} required">
	<label for="tickLength">
		<g:message code="tick.tickLength.label" default="Tick Length" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="tickLength" type="number" value="${tick.tickLength}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: tick, field: 'active', 'error')} ">
	<label for="active">
		<g:message code="tick.active.label" default="Active" />
		
	</label>
	<g:checkBox name="active" value="${tick?.active}" />
</div>

<div class="fieldcontain ${hasErrors(bean: tick, field: 'currentTick', 'error')} required">
	<label for="currentTick">
		<g:message code="tick.currentTick.label" default="Current Tick" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="currentTick" type="number" value="${tick.currentTick}" required=""/>
</div>

