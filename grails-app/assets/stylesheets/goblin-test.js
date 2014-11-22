function Goblin(config) {
    this.loggedIn = false;
    this.url = config.get('url');
    this.username = config.get('username');
    this.password = config.get('password');
}

/**
 * Connect to Little Goblin. Inspired (copied) from Cinnamon.js
 * @param errorHandler an optional function which is used to display a login error.
 * If not set, use the default connectionError function.
 */
Goblin.prototype.connect = function (errorHandler) {
    var self = this;
    self.loginError = errorHandler ? errorHandler : self.connectionError;
    $.ajax(this.url + 'j_spring_security_check', {
        async: false,
        type: 'post',
        success: function (data) {
            self.loggedIn = true;
        },
        data: {
            j_username: this.username,
            j_password: this.password,
        },
        statusCode: {
            500: self.loginError
        }
    })
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
                result = "<nothing/>" ;
            }
        }
    });
    return result;
};