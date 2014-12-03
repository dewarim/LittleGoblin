var GOBLIN_NO_RESULT = "<nothing/>";

function Goblin(config) {
    this.loggedIn = false;
    if (config) {
        this.url = config.get('url');
        this.username = config.get('username');
        this.password = config.get('password');
    }
}

function logger(msg){
    console.log(msg);
}

/**
 * Connect to Little Goblin. Inspired (copied) from Cinnamon.js
 * @param errorHandler an optional function which is used to display a login error.
 * If not set, use the default connectionError function.
 */
Goblin.prototype.connect = function (errorHandler) {
    var self = this;
    var result;
    self.loginError = errorHandler ? errorHandler : self.connectionError;
    $.ajax(this.url + 'j_spring_security_check', {
        async: false,
        type: 'post',
        success: function (data) {
            self.loggedIn = true;
            result = true;
        },
        error: function (data) {
            result = false;
        },
        data: {
            j_username: this.username,
            j_password: this.password,
        },
        statusCode: {
            500: self.loginError
        }
    })
    return result;
};

Goblin.prototype.isConnected = function () {
    return this.loggedIn;
}

Goblin.prototype.connectionError = function (message) {
    alert(message);
};


Goblin.prototype.goToStart = function () {
    var result = GOBLIN_NO_RESULT;
    var self = this;
    $.ajax(this.url + 'portal/start', {
        type: 'get',
        async: false,
        success: function (data) {
            result =  $('<html/>').html(data);
        },
        statusCode: {
            500: function () {
                console.log("Failed to go to start page for logged in users.")
            }
        }
    });
    return result;
};

/**
 * Go to start page, then click on link to send character Gobli to his starting town.
 * @returns {string} ("<nothing/>" or town result page)
 */
Goblin.prototype.goToTown = function(){
    var result = GOBLIN_NO_RESULT;
    var self = this;
    var startPage = this.goToStart();
    var townLink = startPage.find('a:contains("Gobli")');
    if(townLink.length == 0){
        logger("Could not find link for Gobli.")
        return result;
    }
    
    $.ajax(townLink.attr('href'), {
        type: 'get',
        async: false,
        success: function (data) {
            result = data;
        },
        statusCode: {
            500: function () {
                console.log("Failed to go to town page.")
            }
        }
    });
    return result; 
};

/**
 * Go directly to the requested page. Checks the result for a div with id page-info,
 * which needs to contain data elements for controller and action.
 * 
 * Validation logic:
 * 1. div#page-info.data(controller) == controllerName parameter
 * 2. div#page-info.data(action) == actionName parameter
 * 
 * @returns {string} ("<nothing/>" or result page)
 */
Goblin.prototype.getPage = function(controllerName, actionName){
    var result = GOBLIN_NO_RESULT;
    var self = this;
    var url = this.url + controllerName + "/"+actionName
    console.log("getPage: "+url);
    $.ajax(url, {
        type: 'get',
        async: false,
        dataType:'html',
        success: function (data) {
            result = $('<html/>').html(data);
        },
        statusCode: {
            500: function () {
                console.log("Failed to get page for "+url);
            }
        }
    });
    var pageInfo = $(result).find('#page-info');
    if(pageInfo.length == 0){
        console.log("page-info is missing.");
        return GOBLIN_NO_RESULT;
    }
    if(pageInfo.data('controller') != controllerName || pageInfo.data('action') != actionName){
        console.log("page-Info element does not contain expected controller/action data.");
        console.log("found: "+pageInfo.data('controller')+"/"+pageInfo.data('action'));
        return GOBLIN_NO_RESULT;
    } 
    return result; 
};

Goblin.prototype.isValidResult = function(result){
    return result != GOBLIN_NO_RESULT;
}