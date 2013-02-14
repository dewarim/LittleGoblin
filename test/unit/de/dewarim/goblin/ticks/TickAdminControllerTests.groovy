package de.dewarim.goblin.ticks

import de.dewarim.goblin.admin.TickAdminController
import grails.test.mixin.*

@TestFor(TickAdminController)
@Mock(Tick)
class TickAdminControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/tick/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.tickInstanceList.size() == 0
        assert model.tickInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.tickInstance != null
    }

    void testSave() {
        controller.save()

        assert model.tickInstance != null
        assert view == '/tick/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/tick/show/1'
        assert controller.flash.message != null
        assert Tick.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/tick/list'

        populateValidParams(params)
        def tick = new Tick(params)

        assert tick.save() != null

        params.id = tick.id

        def model = controller.show()

        assert model.tickInstance == tick
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/tick/list'

        populateValidParams(params)
        def tick = new Tick(params)

        assert tick.save() != null

        params.id = tick.id

        def model = controller.edit()

        assert model.tickInstance == tick
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/tick/list'

        response.reset()

        populateValidParams(params)
        def tick = new Tick(params)

        assert tick.save() != null

        // test invalid parameters in update
        params.id = tick.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/tick/edit"
        assert model.tickInstance != null

        tick.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/tick/show/$tick.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        tick.clearErrors()

        populateValidParams(params)
        params.id = tick.id
        params.version = -1
        controller.update()

        assert view == "/tick/edit"
        assert model.tickInstance != null
        assert model.tickInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/tick/list'

        response.reset()

        populateValidParams(params)
        def tick = new Tick(params)

        assert tick.save() != null
        assert Tick.count() == 1

        params.id = tick.id

        controller.delete()

        assert Tick.count() == 0
        assert Tick.get(tick.id) == null
        assert response.redirectedUrl == '/tick/list'
    }
}
