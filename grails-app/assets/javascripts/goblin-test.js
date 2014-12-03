/*
 * Tests for Little Goblin 
 */
function runTests(goblin) {
    goblin.connect();
    QUnit.config.reorder = false;

    QUnit.test("check login", function () {
        var orc = new Goblin();
        equal(false, orc.connect(), "Try to correct without proper configuration.");
        equal(false, orc.isConnected(), "Make sure orc is not connected after failed attempt.");
        ok(goblin.connect(), "Goblin should be able to connect successfully");
        equal(true, goblin.isConnected(), "Check that default Goblin is connected");
    });

    QUnit.test("check for two test characters", function () {
            var startPage = goblin.goToStart();
            ok(startPage.find('a:contains("Gobli")'), "Gobli is missing.");
            ok(startPage.find('a:contains("Alice")'), "Alice is missing.");
        }
    );

    QUnit.test("go to town", function () {
        var result = goblin.goToTown();
        notEqual(result, '<nothing/>', "Check that goToTown did return something");
        var townDiv = $(result).find('div').find('.town');
        ok(townDiv.length > 0);
    });
}
