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
        var result = goblin.goToTown('Gobli');
        notEqual(result, '<nothing/>', "Check that goToTown did return something");
        var townDiv = $(result).find('div').find('.town');
        ok(townDiv.length > 0);
    });

    QUnit.test("enter Grand Melee with both characters", function () {

        doJoinMelee('Gobli');
        doJoinMelee('Alice');
        // TODO: follow up with a test for the Grand Melee itself.
    });
}


function doJoinMelee(name) {
    goblin.goToTown(name);
    var meleePage = goblin.getPage('melee', 'index');
    var link = meleePage.find("a[href*='melee/join']");
    ok(link.length > 0, "Expected link to join Grand Melee");
    var result;
    $.ajax(link.attr('href'), {
        type: 'get',
        async: false,
        dataType:'html',
        success: function (data) {
            result = $('<html/>').html(data);
        },
        fail: {
            500: function () {
                result = $(GOBLIN_NO_RESULT);
                console.log("Failed to get page for "+url);
            }
        }
    });
    ok(result.find("a[href*='melee/leave']").length > 0, "Expected link to leave melee");
}
