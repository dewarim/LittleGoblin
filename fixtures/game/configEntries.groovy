import de.dewarim.goblin.GlobalConfigEntry

fixture {
    createOrderPrice(GlobalConfigEntry, name: 'coins.price.create_order', entryValue: '10')
    chatterBoxes(GlobalConfigEntry, name: 'order.chatterboxes', entryValue: '3')
    chatterBoxMaxMessages(GlobalConfigEntry, name: 'order.chatterbox.max.messages', entryValue: '10')
    priceChatterbox(GlobalConfigEntry, name: 'coins.price.chatterbox', entryValue: '10')

    // the % a pc will get back when canceling a course in an academy.
    academyRefund(GlobalConfigEntry, name: 'academy.refund.percentage', entryValue: '100')
}