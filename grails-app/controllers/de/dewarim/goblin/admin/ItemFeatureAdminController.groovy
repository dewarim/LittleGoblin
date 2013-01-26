package de.dewarim.goblin.admin

import de.dewarim.goblin.BaseController
import de.dewarim.goblin.item.ItemTypeFeature
import grails.plugins.springsecurity.Secured
import de.dewarim.goblin.Feature
import de.dewarim.goblin.item.ItemType

@Secured(["ROLE_ADMIN"])
class ItemFeatureAdminController extends BaseController {

  def index() {
    return [
            itemFeatures: ItemTypeFeature.listOrderById()
    ]
  }

  def edit() {
    def itemFeature = ItemTypeFeature.get(params.id)
    if (!itemFeature) {
      return render(status: 503, text: message(code: 'error.unknown.itemFeature'))
    }
    render(template: '/itemFeatureAdmin/edit', model: [itemFeature: itemFeature])
  }

  def cancelEdit() {
    def itemFeature = ItemTypeFeature.get(params.id)
    if (!itemFeature) {
      return render(status: 503, text: message(code: 'error.unknown.itemFeature'))
    }
    render(template: '/itemFeatureAdmin/row', model: [itemFeature: itemFeature])
  }

  def update() {
    try {
      def itemFeature = ItemTypeFeature.get(params.id)
      if (!itemFeature) {
        throw new RuntimeException('error.unknown.itemFeature')
      }
      updateFields itemFeature
      itemFeature.save()
      render(template: '/itemFeatureAdmin/row', model: [itemFeature: itemFeature])
    }
    catch (RuntimeException e) {
      renderException e
    }
  }

  protected void updateFields(itemFeature) {
    itemFeature.feature = inputValidationService.checkObject(Feature.class, params.feature)  // TODO: param is script.
    itemFeature.itemType = inputValidationService.checkObject(ItemType.class, params.itemType)
    itemFeature.config = inputValidationService.checkXmlText(params.config)
  }

  def save() {
    ItemTypeFeature itemFeature = new ItemTypeFeature()
    try {
      updateFields(itemFeature)
      itemFeature.save()
      render(template: '/itemFeatureAdmin/list', model: [itemFeatures: ItemTypeFeature.listOrderById()])
    }
    catch (RuntimeException e) {
      renderException(e)
    }
  }

  def delete() {
    ItemTypeFeature itemFeature = ItemTypeFeature.get(params.id)
    try {
      if (!itemFeature) {
        throw new RuntimeException("error.object.not.found")
      }
      itemFeature.delete()
      render(text: message(code: 'itemFeature.deleted'))
    }
    catch (RuntimeException e) {
      renderException e
    }
  }

}
