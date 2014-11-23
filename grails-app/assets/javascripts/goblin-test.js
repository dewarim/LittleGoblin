/*
 * Tests for Little Goblin 
 */
function runTests(goblin) {
    goblin.connect();
    QUnit.config.reorder = false;
    
    test("check login", function () {
        var orc = new Goblin();
        equal(false, orc.connect(), "Try to correct without proper configuration.");
        equal(false, orc.isConnected(), "Make sure orc is not connected after failed attempt.");
        ok(goblin.connect(), "Goblin should be able to connect successfully");
        equal(true, goblin.isConnected(), "Check that default Goblin is connected");
    });
    
}
