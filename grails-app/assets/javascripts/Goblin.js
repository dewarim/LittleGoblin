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
    var result = "<nothing/>";
    var self = this;
    $.ajax(this.url + 'portal/start', {
        type: 'get',
        async: false,
        success: function (data) {
            result = data;
        },
        statusCode: {
            500: function () {
                console.log("Failed to go to start page for logged in users.")
                result = "<nothing/>";
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
    var result = "<nothing/>";
    var self = this;
    var startPage = this.goToStart();
    var townLink = $(startPage).find('a:contains("Gobli")');
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
                result = "<nothing/>";
            }
        }
    });
    return result; 
};